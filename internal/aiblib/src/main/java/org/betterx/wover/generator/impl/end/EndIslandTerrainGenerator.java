package org.aiblib.wover.generator.impl.end;

import org.aiblib.bclib.util.MHelper;
import org.aiblib.wover.biome.api.BiomeManager;
import org.aiblib.wover.biome.impl.modification.ChunkGeneratorHelper;
import org.aiblib.wover.biome.mixin.ChunkGeneratorAccessor;
import org.aiblib.wover.common.generator.api.biomesource.BiomeSourceWithConfig;
import org.aiblib.wover.common.generator.api.biomesource.NoiseGeneratorSettingsProvider;
import org.aiblib.wover.generator.api.biomesource.WoverBiomeData;
import org.aiblib.wover.generator.api.biomesource.end.WoverEndConfig;
import org.aiblib.wover.generator.impl.biomesource.end.WoverEndBiomeSource;
import org.aiblib.wover.generator.mixin.generator.NoiseChunkAccessor;
import org.aiblib.wover.generator.mixin.generator.NoiseInterpolatorAccessor;
import org.aiblib.wover.math.api.noise.OpenSimplexNoise;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

import com.google.common.collect.Lists;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.jetbrains.annotations.Nullable;

public class EndIslandTerrainGenerator {
    private static final int MAX_TERRAIN_BOOL_CACHE_SECTIONS = 1024;
    private static final int MAX_BIOME_DATA_CACHE_COLUMNS = 8192;
    private static final Map<Long, TerrainBoolCache> TERRAIN_BOOL_CACHE_MAP = new LinkedHashMap<>(
            128,
            0.75F,
            true
    ) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, TerrainBoolCache> eldest) {
            return size() > MAX_TERRAIN_BOOL_CACHE_SECTIONS;
        }
    };
    private static final Map<Long, WoverBiomeData> BIOME_DATA_CACHE = new LinkedHashMap<>(128, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, WoverBiomeData> eldest) {
            return size() > MAX_BIOME_DATA_CACHE_COLUMNS;
        }
    };
    private static final Object TERRAIN_BOOL_CACHE_LOCK = new Object();
    private static final Object BIOME_DATA_CACHE_LOCK = new Object();
    private static final ThreadLocal<IslandSampler> IS_LAND_SAMPLER = ThreadLocal.withInitial(IslandSampler::new);
    private static final ReentrantLock LOCKER = new ReentrantLock();
    private static final double SCALE_XZ = 8.0;
    private static final double SCALE_Y = 4.0;
    private static final float[] COEF;
    private static final Point[] OFFS;

    private static IslandLayer largeIslands;
    private static IslandLayer mediumIslands;
    private static IslandLayer smallIslands;
    private static OpenSimplexNoise noise1;
    private static OpenSimplexNoise noise2;
    private static BiomeSource biomeSource;
    public static WoverEndConfig config;
    private static Sampler sampler;
    private static EndIslandGeneratorOptions options;
    private static int largeIslandSeed;
    private static int mediumIslandSeed;
    private static int smallIslandSeed;
    private static int firstNoiseSeed;
    private static int secondNoiseSeed;
    private static volatile int terrainGenerationId;

    public static void initNoise(
            long seed,
            BiomeSource biomeSource,
            Sampler sampler,
            EndIslandGeneratorOptions options
    ) {
        EndIslandTerrainGenerator.config = resolveEndConfig(biomeSource);
        EndIslandTerrainGenerator.options = options;
        if (options == null || !options.enabled()) {
            largeIslands = null;
            mediumIslands = null;
            smallIslands = null;
            noise1 = null;
            noise2 = null;
            EndIslandTerrainGenerator.biomeSource = null;
            EndIslandTerrainGenerator.sampler = null;
            return;
        }

        RandomSource random = new LegacyRandomSource(seed);
        largeIslandSeed = random.nextInt();
        mediumIslandSeed = random.nextInt();
        smallIslandSeed = random.nextInt();
        firstNoiseSeed = random.nextInt();
        secondNoiseSeed = random.nextInt();
        largeIslands = new IslandLayer(largeIslandSeed, options.bigIslands());
        mediumIslands = new IslandLayer(mediumIslandSeed, options.mediumIslands());
        smallIslands = new IslandLayer(smallIslandSeed, options.smallIslands());
        noise1 = new OpenSimplexNoise(firstNoiseSeed);
        noise2 = new OpenSimplexNoise(secondNoiseSeed);
        synchronized (TERRAIN_BOOL_CACHE_LOCK) {
            TERRAIN_BOOL_CACHE_MAP.clear();
        }
        synchronized (BIOME_DATA_CACHE_LOCK) {
            BIOME_DATA_CACHE.clear();
        }
        terrainGenerationId++;
        EndIslandTerrainGenerator.biomeSource = biomeSource;
        EndIslandTerrainGenerator.sampler = sampler;

    }

    private static @Nullable WoverEndConfig resolveEndConfig(BiomeSource biomeSource) {
        BiomeSource source = biomeSource;
        for (int depth = 0; depth < 8 && source != null; depth++) {
            if (source instanceof BiomeSourceWithConfig bcl) {
                if (bcl.getBiomeSourceConfig() instanceof WoverEndConfig resolved) {
                    return resolved;
                }
            }
            BiomeSource original = BlueprintCompat.unwrapOriginalSource(source);
            if (original != null && original != source) {
                source = original;
                continue;
            }
            break;
        }
        return null;
    }

    public static WoverEndConfig config() {
        return config == null ? WoverEndConfig.MINECRAFT_18 : config;
    }

    public static boolean hasCentralIsland() {
        return options != null && options.generateCentralIsland();
    }

    public static boolean shouldUseEndIslandTerrain(@Nullable WoverEndConfig endConfig, @Nullable EndIslandGeneratorOptions options) {
        return options != null && options.enabled();
    }

    private static WoverEndConfig islandAwareBiomeConfig() {
        WoverEndConfig base = WoverEndConfig.MINECRAFT_18;
        return new WoverEndConfig(
                base.mapVersion,
                WoverEndConfig.EndBiomeGeneratorType.PAULEVS,
                base.withVoidBiomes,
                base.innerVoidRadiusSquared,
                base.centerBiomesSize,
                base.voidBiomesSize,
                base.landBiomesSize,
                base.barrensBiomesSize
        );
    }

    private static BiomeSource ensureIslandAwareEndBiomeSource(
            ChunkGenerator chunkGenerator,
            Holder<NoiseGeneratorSettings> settingsHolder,
            long seed
    ) {
        BiomeSource currentSource = chunkGenerator.getBiomeSource();
        WoverEndConfig endConfig = resolveEndConfig(currentSource);
        if (endConfig != null && endConfig.generatorVersion == WoverEndConfig.EndBiomeGeneratorType.PAULEVS) {
            if (currentSource instanceof WoverEndBiomeSource source) {
                source.setSeed(seed);
            }
            return currentSource;
        }

        WoverEndBiomeSource source = new WoverEndBiomeSource(islandAwareBiomeConfig());
        if (settingsHolder.isBound()) {
            source.onLoadGeneratorSettings(settingsHolder.value());
        }
        if (!(currentSource instanceof TheEndBiomeSource)) {
            source.setSeed(seed);
            source.mergeWithBiomeSource(currentSource);
        } else {
            if (seed == 0) {
                source.reloadBiomes();
            } else {
                source.setSeed(seed);
            }
        }

        if (chunkGenerator instanceof ChunkGeneratorAccessor accessor) {
            accessor.wover_setBiomeSource(source);
            ChunkGeneratorHelper.rebuildFeaturesPerStep(chunkGenerator, source);
        }

        return source;
    }

    public static void fillTerrainDensity(double[] buffer, int posX, int posZ, int scaleXZ, int scaleY, int maxHeight) {
        long profileStart = EndWorldgenProfiler.start();
        if (largeIslands == null || mediumIslands == null || smallIslands == null || noise1 == null || noise2 == null) {
            return;
        }
        LOCKER.lock();
        try {
            final float fadeOutDist = 27.0f;
            final float fadOutStart = maxHeight - (fadeOutDist + 1);
            largeIslands.clearCache();
            mediumIslands.clearCache();
            smallIslands.clearCache();

            int x = posX / scaleXZ;
            int z = posZ / scaleXZ;
            double distortion1 = noise1.eval(x * 0.1, z * 0.1) * 20 + noise2.eval(
                    x * 0.2,
                    z * 0.2
            ) * 10 + noise1.eval(x * 0.4, z * 0.4) * 5;
            double distortion2 = noise2.eval(x * 0.1, z * 0.1) * 20 + noise1.eval(
                    x * 0.2,
                    z * 0.2
            ) * 10 + noise2.eval(x * 0.4, z * 0.4) * 5;
            double px = (double) x * scaleXZ + distortion1;
            double pz = (double) z * scaleXZ + distortion2;

            largeIslands.updatePositions(px, pz, maxHeight);
            mediumIslands.updatePositions(px, pz, maxHeight);
            smallIslands.updatePositions(px, pz, maxHeight);

            float terrainHeight = getAverageDepth(x << 1, z << 1) * 0.5F;

            for (int y = 0; y < buffer.length; y++) {
                double py = (double) y * scaleY;
                float dist = largeIslands.getDensity(px, py, pz, terrainHeight);
                dist = dist > 1 ? dist : MHelper.max(dist, mediumIslands.getDensity(px, py, pz, terrainHeight));
                dist = dist > 1 ? dist : MHelper.max(dist, smallIslands.getDensity(px, py, pz, terrainHeight));
                if (dist > -0.5F) {
                    dist += (float) (noise1.eval(px * 0.01, py * 0.01, pz * 0.01) * 0.02 + 0.02);
                    dist += (float) (noise2.eval(px * 0.05, py * 0.05, pz * 0.05) * 0.01 + 0.01);
                    dist += (float) (noise1.eval(px * 0.1, py * 0.1, pz * 0.1) * 0.005 + 0.005);
                }

                if (py >= maxHeight) dist = -1;
                else if (py > fadOutStart) {
                    dist = (float) Mth.lerp((py - fadOutStart) / fadeOutDist, dist, -1);
                }
                buffer[y] = dist;
            }
        } finally {
            LOCKER.unlock();
            EndWorldgenProfiler.terrainDensity(profileStart);
        }
    }

    private static float getAverageDepth(int x, int z) {
        long profileStart = EndWorldgenProfiler.start();
        if (biomeSource == null) {
            return 0;
        }
        try {
            WoverBiomeData biome = getBiomeData(biomeSource, x, z);
            if (biome != null && biome.terrainHeight < 0.1F) {
                return 0F;
            }
            float depth = 0F;
            for (int i = 0; i < OFFS.length; i++) {
                int px = x + OFFS[i].x;
                int pz = z + OFFS[i].y;
                biome = getBiomeData(biomeSource, px, pz);
                depth += biome == null ? 0 : (biome.terrainHeight * COEF[i]);
            }
            return depth;
        } finally {
            EndWorldgenProfiler.averageDepth(profileStart);
        }
    }

    private static @Nullable WoverBiomeData getBiomeData(BiomeSource biomeSource, int x, int z) {
        long profileStart = EndWorldgenProfiler.start();
        long key = columnKey(x, z);
        synchronized (BIOME_DATA_CACHE_LOCK) {
            WoverBiomeData cached = BIOME_DATA_CACHE.get(key);
            if (cached != null) {
                EndWorldgenProfiler.biomeData(profileStart, true);
                return cached;
            }
        }
        WoverBiomeData biome = biomeSource instanceof WoverEndBiomeSource endSource
                ? endSource.getWoverBiomeData(x, 0, z, sampler)
                : (BiomeManager.biomeDataForHolder(biomeSource.getNoiseBiome(x, 0, z, sampler)) instanceof WoverBiomeData data
                        ? data
                        : null);
        if (biome != null) {
            synchronized (BIOME_DATA_CACHE_LOCK) {
                BIOME_DATA_CACHE.put(key, biome);
            }
            EndWorldgenProfiler.biomeData(profileStart, false);
            return biome;
        }
        EndWorldgenProfiler.biomeData(profileStart, false);
        return null;
    }

    static {
        float sum = 0;
        List<Float> coef = Lists.newArrayList();
        List<Point> pos = Lists.newArrayList();
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                float dist = MHelper.length(x, z) / 3F;
                if (dist <= 1) {
                    sum += dist;
                    coef.add(dist);
                    pos.add(new Point(x, z));
                }
            }
        }
        OFFS = pos.toArray(new Point[]{});
        COEF = new float[coef.size()];
        for (int i = 0; i < COEF.length; i++) {
            COEF[i] = coef.get(i) / sum;
        }
    }

    public static Boolean isLand(int x, int z, int maxHeight) {
        long profileStart = EndWorldgenProfiler.start();
        if (largeIslands == null || mediumIslands == null || smallIslands == null || noise1 == null || noise2 == null) {
            return false;
        }
        int sectionX = TerrainBoolCache.scaleCoordinate(x);
        int sectionZ = TerrainBoolCache.scaleCoordinate(z);
        final int stepY = (int) Math.ceil(maxHeight / SCALE_Y);
        final long sectionKey = sectionKey(sectionX, sectionZ);
        TerrainBoolCache section;
        synchronized (TERRAIN_BOOL_CACHE_LOCK) {
            section = TERRAIN_BOOL_CACHE_MAP.get(sectionKey);
            if (section == null) {
                section = new TerrainBoolCache();
                TERRAIN_BOOL_CACHE_MAP.put(sectionKey, section);
            }
            byte value = section.getData(x, z);
            if (value > 0) {
                EndWorldgenProfiler.isLand(profileStart, true);
                return value > 1;
            }
        }

        IslandSampler islandSampler = IS_LAND_SAMPLER.get();
        islandSampler.ensureCurrent();

        double px = (x >> 1) + 0.5;
        double pz = (z >> 1) + 0.5;

        double distortion1 = islandSampler.noise1.eval(px * 0.1, pz * 0.1) * 20 + islandSampler.noise2.eval(
                px * 0.2,
                pz * 0.2
        ) * 10 + islandSampler.noise1.eval(px * 0.4, pz * 0.4) * 5;
        double distortion2 = islandSampler.noise2.eval(px * 0.1, pz * 0.1) * 20 + islandSampler.noise1.eval(
                px * 0.2,
                pz * 0.2
        ) * 10 + islandSampler.noise2.eval(px * 0.4, pz * 0.4) * 5;
        px = px * SCALE_XZ + distortion1;
        pz = pz * SCALE_XZ + distortion2;

        islandSampler.largeIslands.updatePositions(px, pz, maxHeight);
        islandSampler.mediumIslands.updatePositions(px, pz, maxHeight);
        islandSampler.smallIslands.updatePositions(px, pz, maxHeight);

        boolean result = false;
        for (int y = 0; y < stepY; y++) {
            double py = (double) y * SCALE_Y;
            float dist = islandSampler.largeIslands.getDensity(px, py, pz);
            dist = dist > 1 ? dist : MHelper.max(dist, islandSampler.mediumIslands.getDensity(px, py, pz));
            dist = dist > 1 ? dist : MHelper.max(dist, islandSampler.smallIslands.getDensity(px, py, pz));
            if (dist > -0.5F) {
                dist += (float) (islandSampler.noise1.eval(px * 0.01, py * 0.01, pz * 0.01) * 0.02 + 0.02);
                dist += (float) (islandSampler.noise2.eval(px * 0.05, py * 0.05, pz * 0.05) * 0.01 + 0.01);
                dist += (float) (islandSampler.noise1.eval(px * 0.1, py * 0.1, pz * 0.1) * 0.005 + 0.005);
            }
            if (dist > -0.01) {
                result = true;
                break;
            }
        }

        synchronized (TERRAIN_BOOL_CACHE_LOCK) {
            section = TERRAIN_BOOL_CACHE_MAP.get(sectionKey);
            if (section == null) {
                section = new TerrainBoolCache();
                TERRAIN_BOOL_CACHE_MAP.put(sectionKey, section);
            }
            section.setData(x, z, (byte) (result ? 2 : 1));
        }
        EndWorldgenProfiler.isLand(profileStart, false);
        return result;
    }

    private static long sectionKey(int sectionX, int sectionZ) {
        return columnKey(sectionX, sectionZ);
    }

    private static long columnKey(int x, int z) {
        return ((long) x << 32) ^ (z & 0xFFFFFFFFL);
    }

    public static void onServerLevelInit(
            ServerLevel level,
            LevelStem levelStem,
            long seed,
            EndIslandGeneratorOptions options
    ) {
        if (level.dimension() == Level.END) {
            final ChunkGenerator chunkGenerator = levelStem.generator();
            BiomeSource biomeSource = chunkGenerator.getBiomeSource();
            if (chunkGenerator instanceof NoiseBasedChunkGenerator) {
                Holder<NoiseGeneratorSettings> sHolder = ((NoiseGeneratorSettingsProvider) chunkGenerator)
                        .wover_getNoiseGeneratorSettingHolders();
                if (options != null && options.enabled()) {
                    biomeSource = ensureIslandAwareEndBiomeSource(chunkGenerator, sHolder, seed);
                }
                WoverEndConfig endConfig = resolveEndConfig(chunkGenerator.getBiomeSource());
                EndTerrainTarget.class
                                .cast(sHolder.value())
                                .all_is_better_lib$setUsesEndIslandTerrain(shouldUseEndIslandTerrain(endConfig, options));

            }
            initNoise(
                    seed,
                    biomeSource,
                    level.getChunkSource().randomState().sampler(),
                    options
            );
        }
    }

    public static void prepareEndLevelStem(LevelStem levelStem, EndIslandGeneratorOptions options, long seed) {
        final ChunkGenerator chunkGenerator = levelStem.generator();
        if (chunkGenerator instanceof NoiseBasedChunkGenerator) {
            Holder<NoiseGeneratorSettings> sHolder = ((NoiseGeneratorSettingsProvider) chunkGenerator)
                    .wover_getNoiseGeneratorSettingHolders();
            if (options != null && options.enabled()) {
                ensureIslandAwareEndBiomeSource(chunkGenerator, sHolder, seed);
            }
            WoverEndConfig endConfig = resolveEndConfig(chunkGenerator.getBiomeSource());
            EndTerrainTarget.class
                            .cast(sHolder.value())
                            .all_is_better_lib$setUsesEndIslandTerrain(shouldUseEndIslandTerrain(endConfig, options));
        }
    }

    public static void fillSlice(
            boolean primarySlice,
            int x,
            List<NoiseChunk.NoiseInterpolator> interpolators,
            NoiseChunkAccessor accessor,
            NoiseSettings noiseSettings
    ) {
        final int sizeY = noiseSettings.getCellHeight();
        final int sizeXZ = noiseSettings.getCellWidth();
        final int cellSizeXZ = accessor.all_is_better_lib$getCellCountXZ() + 1;
        final int firstCellZ = accessor.all_is_better_lib$getFirstCellZ();

        x *= sizeXZ;
        for (int cellXZ = 0; cellXZ < cellSizeXZ; ++cellXZ) {
            int z = (firstCellZ + cellXZ) * sizeXZ;
            for (NoiseChunk.NoiseInterpolator noiseInterpolator : interpolators) {
                if (noiseInterpolator instanceof NoiseInterpolatorAccessor interpolator) {
                    final double[] ds = (primarySlice
                            ? interpolator.all_is_better_lib$getSlice0()
                            : interpolator.all_is_better_lib$getSlice1())[cellXZ];
                    fillTerrainDensity(ds, x, z, sizeXZ, sizeY, noiseSettings.height());
                }
            }
        }
    }

    private static final class BlueprintCompat {
        private static final String CLASS_NAME = "com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSource";
        private static final String FIELD_NAME = "originalSource";
        private static volatile boolean initialized;
        private static volatile Class<?> moddedBiomeSourceClass;
        private static volatile Field originalSourceField;

        private BlueprintCompat() {
        }

        @Nullable
        static BiomeSource unwrapOriginalSource(BiomeSource source) {
            ensureInitialized();
            if (moddedBiomeSourceClass == null || originalSourceField == null) {
                return null;
            }
            if (!moddedBiomeSourceClass.isInstance(source)) {
                return null;
            }
            try {
                Object original = originalSourceField.get(source);
                return original instanceof BiomeSource biomeSource ? biomeSource : null;
            } catch (IllegalAccessException ignored) {
                return null;
            }
        }

        private static void ensureInitialized() {
            if (initialized) {
                return;
            }
            initialized = true;
            try {
                Class<?> clazz = Class.forName(CLASS_NAME, false, BlueprintCompat.class.getClassLoader());
                Field field = clazz.getDeclaredField(FIELD_NAME);
                field.setAccessible(true);
                moddedBiomeSourceClass = clazz;
                originalSourceField = field;
            } catch (ClassNotFoundException | NoSuchFieldException ignored) {
                moddedBiomeSourceClass = null;
                originalSourceField = null;
            }
        }
    }

    private static final class IslandSampler {
        private int generationId = Integer.MIN_VALUE;
        private IslandLayer largeIslands;
        private IslandLayer mediumIslands;
        private IslandLayer smallIslands;
        private OpenSimplexNoise noise1;
        private OpenSimplexNoise noise2;

        private void ensureCurrent() {
            if (generationId == terrainGenerationId) {
                return;
            }
            generationId = terrainGenerationId;
            largeIslands = new IslandLayer(largeIslandSeed, options.bigIslands());
            mediumIslands = new IslandLayer(mediumIslandSeed, options.mediumIslands());
            smallIslands = new IslandLayer(smallIslandSeed, options.smallIslands());
            noise1 = new OpenSimplexNoise(firstNoiseSeed);
            noise2 = new OpenSimplexNoise(secondNoiseSeed);
        }
    }
}

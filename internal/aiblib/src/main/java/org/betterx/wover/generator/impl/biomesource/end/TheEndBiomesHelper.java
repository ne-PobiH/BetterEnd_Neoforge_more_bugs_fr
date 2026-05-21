package org.aiblib.wover.generator.impl.biomesource.end;

import org.aiblib.wover.state.api.WorldState;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;

public class TheEndBiomesHelper {
    private static final int SELECTOR_REGION_SIZE = 256;
    private static final int SELECTOR_CELL_SIZE = 256;
    private static final double SELECTOR_TEMPERATURE_WEIGHT = 0.35;
    private static final double SELECTOR_REGION_JITTER = 0.42;
    private static final double SELECTOR_BORDER_WARP = 0.65;

    private static final WeightedBiomeList MAIN_ISLAND = new WeightedBiomeList();
    private static final WeightedBiomeList HIGHLANDS = new WeightedBiomeList();
    private static final WeightedBiomeList SMALL_ISLANDS = new WeightedBiomeList();
    private static final WeightedBiomeList MIDLANDS_ALL = new WeightedBiomeList();
    private static final WeightedBiomeList BARRENS_ALL = new WeightedBiomeList();
    private static final Map<ResourceKey<Biome>, WeightedBiomeList> MIDLANDS_BY_HIGHLAND = new HashMap<>();
    private static final Map<ResourceKey<Biome>, WeightedBiomeList> BARRENS_BY_HIGHLAND = new HashMap<>();

    static {
        addMainIslandBiome(Biomes.THE_END, 1.0);
        addHighlandsBiome(Biomes.END_HIGHLANDS, 1.0);
        addMidlandsBiome(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, 1.0);
        addSmallIslandsBiome(Biomes.SMALL_END_ISLANDS, 1.0);
        addBarrensBiome(Biomes.END_HIGHLANDS, Biomes.END_BARRENS, 1.0);
    }

    @ApiStatus.Internal
    public static synchronized void addMainIslandBiome(ResourceKey<Biome> biome, double weight) {
        MAIN_ISLAND.add(biome, weight);
    }

    @ApiStatus.Internal
    public static synchronized void addSmallIslandsBiome(ResourceKey<Biome> biome, double weight) {
        SMALL_ISLANDS.add(biome, weight);
    }

    @ApiStatus.Internal
    public static synchronized void addHighlandsBiome(ResourceKey<Biome> biome, double weight) {
        HIGHLANDS.add(biome, weight);
    }

    @ApiStatus.Internal
    public static synchronized void addMidlandsBiome(ResourceKey<Biome> highlands, ResourceKey<Biome> midlands, double weight) {
        if (highlands == null || midlands == null || weight <= 0.0) return;
        if (!HIGHLANDS.contains(highlands)) {
            HIGHLANDS.add(highlands, 1.0);
        }
        MIDLANDS_BY_HIGHLAND.computeIfAbsent(highlands, key -> new WeightedBiomeList()).add(midlands, weight);
        MIDLANDS_ALL.add(midlands, weight);
    }

    @ApiStatus.Internal
    public static synchronized void addBarrensBiome(ResourceKey<Biome> highlands, ResourceKey<Biome> barrens, double weight) {
        if (highlands == null || barrens == null || weight <= 0.0) return;
        if (!HIGHLANDS.contains(highlands)) {
            HIGHLANDS.add(highlands, 1.0);
        }
        BARRENS_BY_HIGHLAND.computeIfAbsent(highlands, key -> new WeightedBiomeList()).add(barrens, weight);
        BARRENS_ALL.add(barrens, weight);
    }

    public static boolean canGenerateAsMainIslandBiome(ResourceKey<Biome> biome) {
        return MAIN_ISLAND.contains(biome);
    }

    public static boolean canGenerateAsSmallIslandsBiome(ResourceKey<Biome> biome) {
        return SMALL_ISLANDS.contains(biome);
    }

    public static boolean canGenerateAsHighlandsBiome(ResourceKey<Biome> biome) {
        return HIGHLANDS.contains(biome);
    }

    public static boolean canGenerateAsEndMidlands(ResourceKey<Biome> biome) {
        return MIDLANDS_ALL.contains(biome);
    }

    public static boolean canGenerateAsEndBarrens(ResourceKey<Biome> biome) {
        return BARRENS_ALL.contains(biome);
    }

    public static boolean canGenerateInEnd(ResourceKey<Biome> biome) {
        return canGenerateAsHighlandsBiome(biome)
                || canGenerateAsEndBarrens(biome)
                || canGenerateAsEndMidlands(biome)
                || canGenerateAsSmallIslandsBiome(biome)
                || canGenerateAsMainIslandBiome(biome);
    }

    @ApiStatus.Internal
    public static ResourceKey<Biome> pickMainIslandKey(Climate.Sampler sampler, int x, int y, int z) {
        return MAIN_ISLAND.pick(selector(sampler, x, y, z));
    }

    @ApiStatus.Internal
    public static ResourceKey<Biome> pickSmallIslandsKey(Climate.Sampler sampler, int x, int y, int z) {
        return SMALL_ISLANDS.pick(selector(sampler, x, y, z));
    }

    @ApiStatus.Internal
    public static ResourceKey<Biome> pickHighlandsKey(Climate.Sampler sampler, int x, int y, int z) {
        return HIGHLANDS.pick(selector(sampler, x, y, z));
    }

    @ApiStatus.Internal
    public static ResourceKey<Biome> pickMidlandsKey(ResourceKey<Biome> highlands, Climate.Sampler sampler, int x, int y, int z) {
        WeightedBiomeList list = MIDLANDS_BY_HIGHLAND.get(highlands);
        ResourceKey<Biome> picked = list != null ? list.pick(selector(sampler, x, y, z)) : null;
        return picked != null ? picked : MIDLANDS_ALL.pick(selector(sampler, x, y, z));
    }

    @ApiStatus.Internal
    public static ResourceKey<Biome> pickBarrensKey(ResourceKey<Biome> highlands, Climate.Sampler sampler, int x, int y, int z) {
        WeightedBiomeList list = BARRENS_BY_HIGHLAND.get(highlands);
        ResourceKey<Biome> picked = list != null ? list.pick(selector(sampler, x, y, z)) : null;
        return picked != null ? picked : BARRENS_ALL.pick(selector(sampler, x, y, z));
    }

    @ApiStatus.Internal
    public static Holder<Biome> resolveHolder(ResourceKey<Biome> key, Holder<Biome> fallback) {
        if (key == null) return fallback;
        RegistryAccess access = WorldState.allStageRegistryAccess();
        Registry<Biome> registry = access == null ? null : access.registry(Registries.BIOME).orElse(null);
        return registry == null
                ? fallback
                : registry.getHolder(key).map(holder -> (Holder<Biome>) holder).orElse(fallback);
    }

    @ApiStatus.Internal
    public static void addAllPossibleBiomes(Collection<Holder<Biome>> holders) {
        RegistryAccess access = WorldState.allStageRegistryAccess();
        Registry<Biome> registry = access == null ? null : access.registry(Registries.BIOME).orElse(null);
        if (registry == null) return;
        for (ResourceKey<Biome> key : allKeys()) {
            registry.getHolder(key).ifPresent(holders::add);
        }
    }

    private static synchronized Set<ResourceKey<Biome>> allKeys() {
        Set<ResourceKey<Biome>> keys = new HashSet<>();
        keys.addAll(MAIN_ISLAND.keys());
        keys.addAll(SMALL_ISLANDS.keys());
        keys.addAll(HIGHLANDS.keys());
        keys.addAll(MIDLANDS_ALL.keys());
        keys.addAll(BARRENS_ALL.keys());
        return keys;
    }

    private static double selector(Climate.Sampler sampler, int x, int y, int z) {
        int warpedX = warpedCoordinate(x, z, true);
        int warpedZ = warpedCoordinate(x, z, false);
        RegionCell region = nearestRegionCell(warpedX, warpedZ);
        int sampleX = region.centerBlockX();
        int sampleZ = region.centerBlockZ();
        double biomeTemperature = temperature(sampler, sampleX, y, sampleZ) * SELECTOR_TEMPERATURE_WEIGHT;
        double localTemperatureOffset = smoothCellNoise(warpedX, warpedZ, SELECTOR_CELL_SIZE);
        double selector = biomeTemperature + localTemperatureOffset;

        return selector - Math.floor(selector);
    }

    private static int warpedCoordinate(int x, int z, boolean xAxis) {
        int dx = xAxis ? 137 : -719;
        int dz = xAxis ? 421 : 263;
        double noise = smoothCellNoise(x + dx, z + dz, SELECTOR_REGION_SIZE) - 0.5;
        return Mth.floor((xAxis ? x : z) + noise * SELECTOR_REGION_SIZE * SELECTOR_BORDER_WARP);
    }

    private static double temperature(Climate.Sampler sampler, int x, int y, int z) {
        DensityFunction.SinglePointContext ctx = new DensityFunction.SinglePointContext(x, y, z);
        double noise = sampler.temperature().compute(ctx);
        return Mth.clamp((noise + 1.0) / 2.0, 0.0, 1.0);
    }

    private static double cellNoise(int x, int z) {
        long value = x * 341873128712L + z * 132897987541L;
        value = (value ^ (value >>> 30)) * -4658895280553007687L;
        value = (value ^ (value >>> 27)) * -7723592293110705685L;
        value = value ^ (value >>> 31);
        return (value & 0x1FFFFFL) / (double) 0x200000L;
    }

    private static double smoothCellNoise(int x, int z, int scale) {
        double sx = x / (double) scale;
        double sz = z / (double) scale;
        int ix = Mth.floor(sx);
        int iz = Mth.floor(sz);
        double fx = smooth(sx - ix);
        double fz = smooth(sz - iz);

        double a = cellNoise(ix, iz);
        double b = cellNoise(ix + 1, iz);
        double c = cellNoise(ix, iz + 1);
        double d = cellNoise(ix + 1, iz + 1);
        return Mth.lerp(fz, Mth.lerp(fx, a, b), Mth.lerp(fx, c, d));
    }

    private static double smooth(double value) {
        return value * value * value * (value * (value * 6.0 - 15.0) + 10.0);
    }

    private static RegionCell nearestRegionCell(int x, int z) {
        double rx = x / (double) SELECTOR_REGION_SIZE;
        double rz = z / (double) SELECTOR_REGION_SIZE;
        int cellX = Mth.floor(rx);
        int cellZ = Mth.floor(rz);
        RegionCell nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int ox = -1; ox <= 1; ox++) {
            for (int oz = -1; oz <= 1; oz++) {
                RegionCell candidate = new RegionCell(cellX + ox, cellZ + oz);
                double dx = rx - candidate.centerX();
                double dz = rz - candidate.centerZ();
                double distance = dx * dx + dz * dz;
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = candidate;
                }
            }
        }

        return nearest == null ? new RegionCell(cellX, cellZ) : nearest;
    }

    private record RegionCell(int x, int z) {
        double centerX() {
            return x + 0.5 + (cellNoise(x * 2 + 17, z * 2 - 31) - 0.5) * SELECTOR_REGION_JITTER;
        }

        double centerZ() {
            return z + 0.5 + (cellNoise(x * 2 - 53, z * 2 + 11) - 0.5) * SELECTOR_REGION_JITTER;
        }

        int centerBlockX() {
            return Mth.floor(centerX() * SELECTOR_REGION_SIZE);
        }

        int centerBlockZ() {
            return Mth.floor(centerZ() * SELECTOR_REGION_SIZE);
        }
    }

    private static final class WeightedBiomeList {
        private final LinkedHashMap<ResourceKey<Biome>, Double> weights = new LinkedHashMap<>();
        private double totalWeight = 0.0;

        void add(ResourceKey<Biome> biome, double weight) {
            if (biome == null || weight <= 0.0) return;
            double next = weights.getOrDefault(biome, 0.0) + weight;
            weights.put(biome, next);
            totalWeight += weight;
        }

        boolean contains(ResourceKey<Biome> biome) {
            return biome != null && weights.containsKey(biome);
        }

        Set<ResourceKey<Biome>> keys() {
            return weights.keySet();
        }

        ResourceKey<Biome> pick(double selector) {
            if (weights.isEmpty()) return null;
            double target = selector * totalWeight;
            ResourceKey<Biome> last = null;
            for (Map.Entry<ResourceKey<Biome>, Double> entry : weights.entrySet()) {
                last = entry.getKey();
                target -= entry.getValue();
                if (target <= 0.0) return entry.getKey();
            }
            return last;
        }
    }
}

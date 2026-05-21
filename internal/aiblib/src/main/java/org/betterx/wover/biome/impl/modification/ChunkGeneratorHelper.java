package org.aiblib.wover.biome.impl.modification;

import org.aiblib.wover.biome.mixin.ChunkGeneratorAccessor;
import org.aiblib.wover.entrypoint.LibWoverBiome;
import org.aiblib.wover.generator.impl.biomesource.end.TheEndBiomesHelper;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;

import net.neoforged.neoforge.common.util.Lazy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class ChunkGeneratorHelper {
    private static List<Holder<Biome>> possibleBiomesForFeatureSorting(BiomeSource biomeSource) {
        Set<Holder<Biome>> possibleBiomes = new LinkedHashSet<>(biomeSource.possibleBiomes());
        if (biomeSource instanceof TheEndBiomeSource) {
            TheEndBiomesHelper.addAllPossibleBiomes(possibleBiomes);
        }
        return List.copyOf(possibleBiomes);
    }

    public static void rebuildFeaturesPerStep(ChunkGenerator generator, BiomeSource biomeSource) {
        if (generator instanceof ChunkGeneratorAccessor acc) {
            Function<Holder<Biome>, BiomeGenerationSettings> function
                    = (Holder<Biome> biomeHolder) -> biomeHolder.value().getGenerationSettings();
            acc.wover_setFeaturesPerStep(Lazy.of(() -> {
                try {
                    List<Holder<Biome>> possibleBiomes = possibleBiomesForFeatureSorting(biomeSource);
                    return FeatureSorter.buildFeaturesPerStep(
                            possibleBiomes,
                            (hh) -> function.apply(hh).features(),
                            true
                    );
                } catch (IllegalStateException e) {
                    var message = e.getMessage();
                    LibWoverBiome.C.LOG.error("Failed to rebuild features per step", e);
                    for (Holder<Biome> biome : possibleBiomesForFeatureSorting(biomeSource)) {
                        var loc = biome.unwrapKey().orElseThrow().location().toString();
                        if (!message.contains(loc)) continue;
                        var res = biome.value().getGenerationSettings();
                        LibWoverBiome.C.LOG.verbose(loc);
                        int ct = 0;
                        for (var feature : res.features()) {
                            LibWoverBiome.C.LOG.verbose("  -------" + ct + "-------");
                            ct++;
                            for (int i = 0; i < feature.size(); i++) {
                                LibWoverBiome.C.LOG.verbose("    + " + feature
                                        .get(i)
                                        .unwrapKey()
                                        .orElseThrow()
                                        .location()
                                        .toString());
                            }
                        }
                    }
                    throw e;
                }
            }));
        }
    }
}

package org.aiblib.wover.biome.impl;

import org.aiblib.wover.biome.api.builder.BiomeBootstrapContext;
import org.aiblib.wover.biome.api.builder.BiomeBuilder;
import org.aiblib.wover.biome.api.data.BiomeData;
import org.aiblib.wover.core.api.registry.CustomBootstrapContext;
import org.aiblib.wover.entrypoint.LibWoverBiome;
import org.aiblib.wover.surface.api.AssignedSurfaceRule;
import org.aiblib.wover.tag.api.event.context.TagBootstrapContext;

import com.mojang.serialization.Lifecycle;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;

import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class BiomeBootstrapContextImpl extends CustomBootstrapContext<Biome, BiomeBootstrapContextImpl> implements BiomeBootstrapContext {
    private final List<BiomeBuilder<?>> registeredBuilders = new LinkedList<>();

    @Override
    public void register(@NotNull BiomeBuilder<?> builder, Lifecycle lifecycle) {
        registeredBuilders.add(builder);
    }

    @ApiStatus.Internal
    public final void bootstrapBiome(BootstrapContext<Biome> context) {
        for (BiomeBuilder<?> builder : registeredBuilders) {
            builder.registerBiome(context);
        }
    }

    @ApiStatus.Internal
    public final void bootstrapBiomeData(BootstrapContext<BiomeData> context) {
        for (BiomeBuilder<?> builder : registeredBuilders) {
            builder.registerBiomeData(context);
        }
    }

    @ApiStatus.Internal
    public final void bootstrapSurfaceRules(BootstrapContext<AssignedSurfaceRule> context) {
        for (BiomeBuilder<?> builder : registeredBuilders) {
            builder.registerSurfaceRule(context);
        }
    }

    public final void prepareTags(TagBootstrapContext<Biome> context) {
        for (BiomeBuilder<?> builder : registeredBuilders) {
            builder.registerBiomeTags(context);
        }
    }

    @Override
    public void onBootstrapContextChange(BiomeBootstrapContextImpl bootstrapContext) {
        LibWoverBiome.C.log.debug("Biome getter changed, resetting bootstrap context");
        BiomeManagerImpl.BOOTSTRAP_BIOMES_WITH_DATA.emit(c -> c.bootstrap(bootstrapContext));
    }
}

package org.aiblib.wover.generator.mixin.generator;

import org.aiblib.wover.generator.impl.chunkgenerator.ChunkGeneratorManagerImpl;

import net.minecraft.world.level.chunk.ChunkGenerator;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorPrintMixin {
    @Override
    public String toString() {
        return ChunkGeneratorManagerImpl.printGeneratorInfo(null, (ChunkGenerator) (Object) this);
    }
}

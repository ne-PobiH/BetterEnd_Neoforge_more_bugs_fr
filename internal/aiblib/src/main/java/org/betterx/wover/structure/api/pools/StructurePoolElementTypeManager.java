package org.aiblib.wover.structure.api.pools;

import org.aiblib.wover.structure.impl.pools.StructurePoolElementTypeManagerImpl;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class StructurePoolElementTypeManager {
    public static <P extends StructurePoolElement> StructurePoolElementType<P> register(
            ResourceLocation location,
            MapCodec<P> codec
    ) {
        return StructurePoolElementTypeManagerImpl.registerExternal(location, codec);
    }
}

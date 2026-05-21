package org.betterx.betterend.util;

import org.aiblib.bclib.util.StructureHelper;
import org.betterx.betterend.BetterEnd;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.io.IOException;
import java.io.InputStream;

public class EndStructureHelper {
    private EndStructureHelper() {
    }

    public static StructureTemplate readStructure(ResourceLocation resource) {
        if (!BetterEnd.MOD_ID.equals(resource.getNamespace())) {
            return StructureHelper.readStructure(resource);
        }
        return readStructureFromBetterEnd(resource);
    }

    public static StructureTemplate readStructure(String path) {
        InputStream stream = BetterEnd.class.getResourceAsStream(path);
        if (stream != null) {
            return readStructureFromStream(path, stream);
        }
        return StructureHelper.readStructure(path);
    }

    private static StructureTemplate readStructureFromBetterEnd(ResourceLocation resource) {
        String path = "/data/" + resource.getNamespace() + "/structure/" + resource.getPath() + ".nbt";
        InputStream stream = BetterEnd.class.getResourceAsStream(path);
        if (stream == null) {
            return StructureHelper.readStructure(resource);
        }
        return readStructureFromStream(path, stream);
    }

    private static StructureTemplate readStructureFromStream(String path, InputStream stream) {
        try (InputStream input = stream) {
            CompoundTag tag = NbtIo.readCompressed(input, NbtAccounter.unlimitedHeap());
            StructureTemplate template = new StructureTemplate();
            template.load(BuiltInRegistries.BLOCK.asLookup(), tag);
            return template;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read structure " + path, e);
        }
    }
}

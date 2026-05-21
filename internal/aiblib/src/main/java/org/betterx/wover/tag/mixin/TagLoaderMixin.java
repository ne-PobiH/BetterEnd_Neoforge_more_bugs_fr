package org.aiblib.wover.tag.mixin;

import org.aiblib.wover.tag.impl.TagManagerImpl;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagLoader;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(TagLoader.class)
public class TagLoaderMixin {
    @Final
    @Shadow
    private String directory;

    private static final Set<String> WOVER_LOGGED_DIRS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @ModifyArg(method = "loadAndBuild", at = @At(value = "INVOKE", target = "Lnet/minecraft/tags/TagLoader;build(Ljava/util/Map;)Ljava/util/Map;"))
    public Map<ResourceLocation, List<TagLoader.EntryWithSource>> wover_modifyTags(Map<ResourceLocation, List<TagLoader.EntryWithSource>> tagsMap) {
        if (WOVER_LOGGED_DIRS.add(directory)) {
            org.aiblib.wover.entrypoint.LibWoverTag.C.LOG.info("TagLoader directory={}", directory);
            if ("tags/block".equals(directory) || "tags/blocks".equals(directory)) {
                ResourceLocation terrain = ResourceLocation.fromNamespaceAndPath("all_is_better_lib", "surfaces/nether/terrain");
                ResourceLocation netherrack = ResourceLocation.fromNamespaceAndPath("all_is_better_lib", "surfaces/nether/netherrack");
                List<TagLoader.EntryWithSource> terrainEntries = tagsMap.get(terrain);
                List<TagLoader.EntryWithSource> netherrackEntries = tagsMap.get(netherrack);
                org.aiblib.wover.entrypoint.LibWoverTag.C.LOG.info(
                        "TagLoader prebuild block tags: terrain={} entries={}, netherrack={} entries={}",
                        terrainEntries == null ? "missing" : terrainEntries.size(),
                        terrainEntries,
                        netherrackEntries == null ? "missing" : netherrackEntries.size(),
                        netherrackEntries
                );
            }
        }
        return TagManagerImpl.didLoadTagMap(directory, tagsMap);
    }
}

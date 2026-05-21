package org.aiblib.bclib.behaviours.interfaces;

import org.aiblib.wover.tabs.api.interfaces.CreativeTabPredicate;

import net.minecraft.world.item.BlockItem;

public interface BehaviourPlantLike extends BlockBehaviour {
    CreativeTabPredicate TAB_PREDICATE = item -> item instanceof BlockItem bi
            && (bi.getBlock() instanceof BehaviourPlantLike
            || bi.getBlock() instanceof BehaviourLeaves);
}

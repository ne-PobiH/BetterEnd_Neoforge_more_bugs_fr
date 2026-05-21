package org.aiblib.wover.potions.api;

import org.aiblib.wover.events.api.Subscriber;

import net.minecraft.world.item.alchemy.PotionBrewing;

public interface OnBootstrapPotions extends Subscriber {
    void bootstrap(PotionBrewing.Builder builder);
}

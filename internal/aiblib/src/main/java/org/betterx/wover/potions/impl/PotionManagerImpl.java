package org.aiblib.wover.potions.impl;

import org.aiblib.wover.events.impl.EventImpl;
import org.aiblib.wover.potions.api.OnBootstrapPotions;

public class PotionManagerImpl {
    public static final EventImpl<OnBootstrapPotions> BOOTSTRAP_POTIONS =
            new EventImpl<>("BOOTSTRAP_POTIONS");
}

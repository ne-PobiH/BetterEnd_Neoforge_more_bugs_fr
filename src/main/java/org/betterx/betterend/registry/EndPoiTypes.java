package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.aiblib.wover.poi.api.PoiManager;
import org.aiblib.wover.poi.api.WoverPoiType;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.RegisterEvent;

public class EndPoiTypes {
    public static WoverPoiType ETERNAL_PORTAL;
    public static WoverPoiType ETERNAL_PORTAL_FRAME;

    public static void register() {
        if (ETERNAL_PORTAL == null && EndBlocks.END_PORTAL_BLOCK != null) {
            ETERNAL_PORTAL = resolveExistingPoiType(EndBlocks.END_PORTAL_BLOCK);
            if (ETERNAL_PORTAL == null) {
                try {
                    ETERNAL_PORTAL = PoiManager.register(
                            BetterEnd.C.mk("eternal_portal"),
                            ImmutableSet.copyOf(EndBlocks.END_PORTAL_BLOCK.getStateDefinition().getPossibleStates()),
                            0, 1
                    );
                } catch (IllegalStateException ex) {
                    BetterEnd.C.log.warn("Skip eternal_portal POI registration: {}", ex.getMessage());
                    ETERNAL_PORTAL = resolveExistingPoiType(EndBlocks.END_PORTAL_BLOCK);
                }
            }
        }
        if (ETERNAL_PORTAL_FRAME == null && EndBlocks.FLAVOLITE_RUNED_ETERNAL != null) {
            ETERNAL_PORTAL_FRAME = resolveExistingPoiType(EndBlocks.FLAVOLITE_RUNED_ETERNAL);
            if (ETERNAL_PORTAL_FRAME == null) {
                try {
                    ETERNAL_PORTAL_FRAME = PoiManager.register(
                            BetterEnd.C.mk("eternal_portal_frame"),
                            Set.of(
                                    EndBlocks.FLAVOLITE_RUNED_ETERNAL.defaultBlockState().setValue(
                                            org.betterx.betterend.blocks.RunedFlavolite.ACTIVATED,
                                            false
                                    )
                            ),
                            0, 1
                    );
                } catch (IllegalStateException ex) {
                    BetterEnd.C.log.warn("Skip eternal_portal_frame POI registration: {}", ex.getMessage());
                    ETERNAL_PORTAL_FRAME = resolveExistingPoiType(EndBlocks.FLAVOLITE_RUNED_ETERNAL);
                }
            }
        }
    }

    public static void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.POINT_OF_INTEREST_TYPE)) {
            register();
        }
    }

    private static WoverPoiType resolveExistingPoiType(Block block) {
        if (block == null) return null;
        Holder<PoiType> holder = findExistingPoiHolder(block);
        if (holder == null) return null;
        ResourceKey<PoiType> key = holder.unwrapKey().orElse(null);
        if (key == null) return null;
        return new WoverPoiType(
                key,
                holder.value(),
                ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates()),
                0,
                1
        );
    }

    private static Holder<PoiType> findExistingPoiHolder(Block block) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            Holder<PoiType> holder = PoiTypes.forState(state).orElse(null);
            if (holder != null) {
                return holder;
            }
        }
        return null;
    }
}

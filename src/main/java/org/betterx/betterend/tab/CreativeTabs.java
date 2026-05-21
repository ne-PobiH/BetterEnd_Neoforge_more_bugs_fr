package org.betterx.betterend.tab;

import org.aiblib.bclib.behaviours.interfaces.BehaviourPlantLike;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.core.registries.Registries;

import net.neoforged.neoforge.registries.RegisterEvent;

public class CreativeTabs {
    private static boolean registered;

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) return;
        if (registered) return;
        registered = true;
        // РіР°СЂР°РЅС‚РёСЂСѓРµРј РґРѕСЃС‚СѓРїРЅРѕСЃС‚СЊ Р±Р»РѕРєРѕРІ/РїСЂРµРґРјРµС‚РѕРІ РґР»СЏ РёРєРѕРЅРѕРє
        org.aiblib.wover.tabs.api.CreativeTabs
                .start(BetterEnd.C)
                .createTab("nature")
                .setPredicate(item -> BehaviourPlantLike.TAB_PREDICATE.contains(item)
                        || item == EndItems.END_LILY_LEAF
                        || item == EndItems.END_LILY_LEAF_DRIED
                )
                .setIcon(EndBlocks.TENANEA_FLOWERS)
                .buildAndAdd()
                .createBlockOnlyTab(EndBlocks.END_MYCELIUM)
                .setIcon(EndBlocks.END_MYCELIUM)
                .buildAndAdd()
                .createItemOnlyTab(EndItems.ETERNAL_CRYSTAL)
                .setIcon(EndItems.ETERNAL_CRYSTAL)
                .buildAndAdd()
                .processRegistries()
                .registerAllTabs();
    }

    public static void register() {
        // no-op; registration happens during the registry event
    }
}

package org.betterx.betterend.integration.emi;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndBlocks;
import org.aiblib.bclib.integration.emi.EMIAnvilRecipe;
import org.aiblib.bclib.recipes.AnvilRecipe;

import net.minecraft.core.Holder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.Item;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiStack;

import java.util.stream.StreamSupport;

@EmiEntrypoint
public class EMIPlugin implements dev.emi.emi.api.EmiPlugin {
    public static final EmiStack INFUSION_WORKSTATION = EmiStack.of(EndBlocks.INFUSION_PEDESTAL);
    public static final EmiStack AZURE_JADESTONE_FURNACE_WORKSTATION = EmiStack.of(EndBlocks.AZURE_JADESTONE.furnace);
    public static final EmiStack SANDY_JADESTONE_FURNACE_WORKSTATION = EmiStack.of(EndBlocks.SANDY_JADESTONE.furnace);
    public static final EmiStack VIRID_JADESTONE_FURNACE_WORKSTATION = EmiStack.of(EndBlocks.VIRID_JADESTONE.furnace);

    public static final EmiRecipeCategory INFUSION_CATEGORY = new EmiRecipeCategory(
            BetterEnd.C.mk("infusion"),
            INFUSION_WORKSTATION,
            org.aiblib.bclib.integration.emi.EMIPlugin.getSprite(0, 16)
    );

    @Override
    public void register(EmiRegistry emiRegistry) {
        final RecipeManager manager = emiRegistry.getRecipeManager();
        emiRegistry.addCategory(INFUSION_CATEGORY);
        emiRegistry.addWorkstation(INFUSION_CATEGORY, INFUSION_WORKSTATION);

        EMIInfusionRecipe.addAllRecipes(emiRegistry, manager);
        if (org.aiblib.bclib.integration.emi.EMIPlugin.END_ALLOYING_CATEGORY != null) {
            EMIBlastingRecipe.addAllRecipes(emiRegistry, manager);
        }

        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, AZURE_JADESTONE_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, SANDY_JADESTONE_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, VIRID_JADESTONE_FURNACE_WORKSTATION);

        org.aiblib.bclib.integration.emi.EMIPlugin bclibPlugin = new org.aiblib.bclib.integration.emi.EMIPlugin();
        bclibPlugin.lazyInit();
        if (org.aiblib.bclib.integration.emi.EMIPlugin.ANVIL_CATEGORIES != null
                && org.aiblib.bclib.integration.emi.EMIPlugin.ANVIL_WORKSTATIONS != null) {
            for (int i = 0; i < org.aiblib.bclib.integration.emi.EMIPlugin.ANVIL_CATEGORIES.length; i++) {
                EmiRecipeCategory category = org.aiblib.bclib.integration.emi.EMIPlugin.ANVIL_CATEGORIES[i];
                if (category != null) {
                    emiRegistry.addCategory(category);
                    emiRegistry.addWorkstation(
                            category,
                            org.aiblib.bclib.integration.emi.EMIPlugin.ANVIL_WORKSTATIONS[i]
                    );
                }
            }
            Iterable<Holder<Item>> hammers = AnvilRecipe.getAllHammers();
            org.aiblib.bclib.integration.emi.EMIPlugin.addAllRecipes(
                    emiRegistry,
                    manager,
                    org.aiblib.bclib.BCLib.LOGGER,
                    AnvilRecipe.TYPE,
                    recipe -> StreamSupport.stream(hammers.spliterator(), false)
                                           .map(Holder::value)
                                           .filter(i -> recipe.value().canUse(i))
                                           .toList(),
                    EMIAnvilRecipe::new
            );
        }
    }
}

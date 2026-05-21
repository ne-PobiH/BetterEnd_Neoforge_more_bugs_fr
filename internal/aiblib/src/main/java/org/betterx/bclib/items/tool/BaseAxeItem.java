package org.aiblib.bclib.items.tool;

import org.aiblib.bclib.client.models.ModelsHelper;
import org.aiblib.bclib.interfaces.ItemModelProvider;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BaseAxeItem extends AxeItem implements ItemModelProvider {
    public BaseAxeItem(Tier material, float attackDamage, float attackSpeed, Properties settings) {
        this(material, settings.attributes(AxeItem.createAttributes(material, attackDamage, attackSpeed)));
    }

    public BaseAxeItem(Tier material, Properties settings) {
        super(material, settings);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockModel getItemModel(ResourceLocation resourceLocation) {
        return ModelsHelper.createHandheldItem(resourceLocation);
    }
}


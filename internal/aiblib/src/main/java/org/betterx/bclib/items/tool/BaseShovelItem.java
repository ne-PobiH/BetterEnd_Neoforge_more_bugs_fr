package org.aiblib.bclib.items.tool;

import org.aiblib.bclib.client.models.ModelsHelper;
import org.aiblib.bclib.interfaces.ItemModelProvider;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BaseShovelItem extends ShovelItem implements ItemModelProvider {
    public BaseShovelItem(Tier material, float attackDamage, float attackSpeed, Properties settings) {
        this(material, settings.attributes(ShovelItem.createAttributes(material, attackDamage, attackSpeed)));
    }

    public BaseShovelItem(Tier material, Properties settings) {
        super(material, settings);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockModel getItemModel(ResourceLocation resourceLocation) {
        return ModelsHelper.createHandheldItem(resourceLocation);
    }
}


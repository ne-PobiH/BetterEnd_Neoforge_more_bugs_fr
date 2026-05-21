package org.aiblib.bclib.registry;

import org.aiblib.bclib.BCLib;
import org.aiblib.bclib.client.render.BaseChestBlockEntityRenderer;
import org.aiblib.bclib.furniture.renderer.RenderChair;
import org.aiblib.bclib.items.boat.BoatTypeOverride;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = BCLib.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BaseBlockEntityRenders {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BaseBlockEntities.CHEST, BaseChestBlockEntityRenderer::new);
        event.registerEntityRenderer(BaseBlockEntities.CHAIR, RenderChair::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition boatModel = BoatModel.createBodyModel();
        LayerDefinition chestBoatModel = ChestBoatModel.createBodyModel();
        LayerDefinition raftModel = RaftModel.createBodyModel();
        LayerDefinition chestRaftModel = ChestRaftModel.createBodyModel();

        BoatTypeOverride.values().forEach(type -> {
            event.registerLayerDefinition(type.boatModelName, () -> type.isRaft ? raftModel : boatModel);
            event.registerLayerDefinition(
                    type.chestBoatModelName,
                    () -> type.isRaft ? chestRaftModel : chestBoatModel
            );
        });
    }
}


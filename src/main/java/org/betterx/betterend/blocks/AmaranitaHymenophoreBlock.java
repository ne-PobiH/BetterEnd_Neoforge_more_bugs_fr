package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.BehaviourBuilders;
import org.aiblib.bclib.blocks.BaseBlock;
import org.aiblib.bclib.client.render.BCLRenderLayer;
import org.aiblib.bclib.interfaces.RenderLayerProvider;

import net.minecraft.world.level.block.SoundType;

public class AmaranitaHymenophoreBlock extends BaseBlock.Wood implements RenderLayerProvider {
    public AmaranitaHymenophoreBlock() {
        super(BehaviourBuilders.createWood().sound(SoundType.WOOD));
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }
}

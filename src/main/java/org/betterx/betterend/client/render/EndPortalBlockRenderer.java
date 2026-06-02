package org.betterx.betterend.client.render;

import org.betterx.betterend.blocks.EndPortalBlock;
import org.betterx.betterend.blocks.entities.EndPortalBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import org.joml.Matrix4f;

public class EndPortalBlockRenderer implements BlockEntityRenderer<EndPortalBlockEntity> {
    private static final float MIN = 6.0F / 16.0F;
    private static final float MAX = 10.0F / 16.0F;

    public EndPortalBlockRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(
            EndPortalBlockEntity blockEntity,
            float tickDelta,
            PoseStack matrices,
            MultiBufferSource buffers,
            int light,
            int overlay
    ) {
        BlockState state = blockEntity.getBlockState();
        Direction.Axis axis = state.getOptionalValue(EndPortalBlock.AXIS).orElse(Direction.Axis.X);

        Matrix4f matrix = matrices.last().pose();
        VertexConsumer consumer = buffers.getBuffer(RenderType.endPortal());
        if (axis == Direction.Axis.Z) {
            renderXFace(consumer, matrix, MIN, false);
            renderXFace(consumer, matrix, MAX, true);
        } else {
            renderZFace(consumer, matrix, MIN, false);
            renderZFace(consumer, matrix, MAX, true);
        }
    }

    private static void renderZFace(VertexConsumer consumer, Matrix4f matrix, float z, boolean reverse) {
        if (reverse) {
            consumer.addVertex(matrix, 0.0F, 0.0F, z);
            consumer.addVertex(matrix, 0.0F, 1.0F, z);
            consumer.addVertex(matrix, 1.0F, 1.0F, z);
            consumer.addVertex(matrix, 1.0F, 0.0F, z);
        } else {
            consumer.addVertex(matrix, 1.0F, 0.0F, z);
            consumer.addVertex(matrix, 1.0F, 1.0F, z);
            consumer.addVertex(matrix, 0.0F, 1.0F, z);
            consumer.addVertex(matrix, 0.0F, 0.0F, z);
        }
    }

    private static void renderXFace(VertexConsumer consumer, Matrix4f matrix, float x, boolean reverse) {
        if (reverse) {
            consumer.addVertex(matrix, x, 0.0F, 1.0F);
            consumer.addVertex(matrix, x, 1.0F, 1.0F);
            consumer.addVertex(matrix, x, 1.0F, 0.0F);
            consumer.addVertex(matrix, x, 0.0F, 0.0F);
        } else {
            consumer.addVertex(matrix, x, 0.0F, 0.0F);
            consumer.addVertex(matrix, x, 1.0F, 0.0F);
            consumer.addVertex(matrix, x, 1.0F, 1.0F);
            consumer.addVertex(matrix, x, 0.0F, 1.0F);
        }
    }
}

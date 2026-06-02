package org.betterx.betterend.blocks.entities;

import org.betterx.betterend.registry.EndBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndPortalBlockEntity extends BlockEntity {
    public EndPortalBlockEntity(BlockPos pos, BlockState state) {
        super(EndBlockEntities.END_PORTAL, pos, state);
    }
}

package org.betterx.betterend.blocks;

import org.aiblib.bclib.behaviours.BehaviourBuilders;
import org.aiblib.bclib.behaviours.interfaces.BehaviourWaterPlant;
import org.aiblib.bclib.interfaces.SurvivesOnSpecialGround;
import org.betterx.betterend.blocks.basis.EndUnderwaterPlantBlock;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CharniaBlock extends EndUnderwaterPlantBlock implements BehaviourWaterPlant {
    public CharniaBlock() {
        super(
                BehaviourBuilders.createWaterPlant()
        );
    }

    @Override
    public void appendHoverText(
            ItemStack itemStack,
            Item.TooltipContext tooltipContext,
            List<Component> list,
            TooltipFlag tooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        SurvivesOnSpecialGround.appendHoverTextUnderwater(list);
    }

    @Override
    protected boolean isTerrain(BlockState state) {
        return state.isSolid();
    }
}

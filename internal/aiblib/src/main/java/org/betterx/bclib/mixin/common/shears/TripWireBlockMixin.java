package org.aiblib.bclib.mixin.common.shears;

import org.aiblib.bclib.items.tool.BaseShearsItem;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.TripWireBlock;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.neoforged.neoforge.common.ItemAbility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TripWireBlock.class)
public class TripWireBlockMixin {
    @WrapOperation(
            method = "playerWillDestroy",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;canPerformAction(Lnet/neoforged/neoforge/common/ItemAbility;)Z"
            )
    )
    private boolean bclib_isShears(ItemStack stack, ItemAbility ability, Operation<Boolean> original) {
        // Preserve vanilla/NeoForge check, then allow custom shears to disarm tripwire.
        return original.call(stack, ability) || BaseShearsItem.isShear(stack);
    }
}




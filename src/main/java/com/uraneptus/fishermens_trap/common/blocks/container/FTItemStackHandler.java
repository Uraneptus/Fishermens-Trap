package com.uraneptus.fishermens_trap.common.blocks.container;

import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FTItemStackHandler extends ItemStackHandler {

    public FTItemStackHandler() {
        super(10);
    }

    public void addItemsAndShrinkBait(List<ItemStack> list, ItemStack baitItem) {
        for (ItemStack itemStack : list) {
            ItemHandlerHelper.insertItemStacked(this, itemStack, false);

            for (int i = 1; i <= getSlots() - 1; i++) {
                ItemStack slot = this.getStackInSlot(i);
                if (ItemHandlerHelper.canItemStacksStackRelaxed(slot, itemStack)) {
                    baitItem.shrink(1);
                }
            }
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return getStackInSlot(slot).getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == 0) {
            return stack.is(FTItemTags.FISH_BAITS);
        }
        return true;
    }

    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }
}

package com.uraneptus.fishermens_trap.common.blocks.container;

import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FTItemStackHandler extends ItemStackHandler {

    public FTItemStackHandler() {
        super(10);
    }

    public void addItemsAndShrinkBait(List<ItemStack> list, ItemStack baitItem) {
        for (ItemStack itemStack : list) {
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < getSlots(); i++) {
                    if (getStackInSlot(i).isEmpty()) {
                        itemStack = insertItem(i, itemStack, false);
                        baitItem.shrink(1);
                        if (itemStack.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot != 0) {
            return 1;
        }
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

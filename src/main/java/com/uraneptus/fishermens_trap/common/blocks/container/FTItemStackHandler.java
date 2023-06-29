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

    public void addItemsToInventory(List<ItemStack> list) {
        for (ItemStack itemStack : list) {
            for (int i = 1; i <= getSlots() - 1; i++) {
                if (insertItem(i, itemStack, false) == ItemStack.EMPTY) {
                    break;
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
        if(slot == 0) {
            return stack.is(FTItemTags.FISH_BAITS);
        }
        return true;
    }

    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }
}

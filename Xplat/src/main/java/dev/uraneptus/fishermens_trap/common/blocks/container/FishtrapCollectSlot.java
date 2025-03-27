package dev.uraneptus.fishermens_trap.common.blocks.container;

import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FishtrapCollectSlot extends Slot {

    public FishtrapCollectSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return !FTConfig.INSTANCE.fullStackCatch() ? 1 : stack.getMaxStackSize();
    }
}

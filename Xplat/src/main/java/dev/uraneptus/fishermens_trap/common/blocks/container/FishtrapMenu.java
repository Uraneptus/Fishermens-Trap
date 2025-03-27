package dev.uraneptus.fishermens_trap.common.blocks.container;

import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FishtrapMenu extends AbstractContainerMenu {
    public final Container container;
    private final ContainerLevelAccess access;

    public FishtrapMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(windowId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainer(10));
    }

    public FishtrapMenu(int pContainerId, Inventory pPlayerInventory, final ContainerLevelAccess pAccess, Container container) {
        super(FTRegistries.FISHTRAP_MENU.get(), pContainerId);
        this.container = container;
        this.access = pAccess;
        checkContainerSize(container, 10);

        this.addSlot(new FishtrapBaitSlot(container, 0, 81, 15));

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new FishtrapCollectSlot(container, i1 + 1, 8 + i1 * 18, 48));
        }

        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(pPlayerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 84));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(pPlayerInventory, i1, 8 + i1 * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, FTRegistries.FISHTRAP_BLOCK.get());
    }
}

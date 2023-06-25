package com.uraneptus.fishermens_trap.common.blocks.container;

import com.uraneptus.fishermens_trap.common.blocks.FishtrapBlockEntity;
import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTMenuType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FishtrapMenu extends AbstractContainerMenu {
    private final Container fishtrap;
    public final FishtrapBlockEntity blockEntity;

    public FishtrapMenu(int pContainerId, Inventory pPlayerInventory, FishtrapBlockEntity blockEntity, Container pContainer) {
        super(FTMenuType.FISHTRAP_MENU.get(), pContainerId);
        this.fishtrap = pContainer;
        this.blockEntity = blockEntity;
        checkContainerSize(pContainer, 10);
        pContainer.startOpen(pPlayerInventory.player);

        this.addSlot(new Slot(pContainer, 0, 81, 15) {

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(FTItemTags.FISH_BAITS);
            }
        });

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(pContainer, i1 + 1, 8 + i1 * 18, 48));
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

    private static FishtrapBlockEntity getTileEntity(Inventory playerInventory, FriendlyByteBuf data) {
        BlockEntity blockEntity = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (blockEntity instanceof FishtrapBlockEntity fishtrapTile) {
            return fishtrapTile;
        } else {
            throw new IllegalStateException("Block entity is not correct! " + blockEntity);
        }
    }

    public FishtrapMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainer(10));
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < this.fishtrap.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.fishtrap.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.fishtrap.getContainerSize(), false)) {
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
        return this.fishtrap.stillValid(pPlayer);
    }
}

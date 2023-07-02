package com.uraneptus.fishermens_trap.common.blocks.container;

import com.mojang.datafixers.util.Pair;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTMenuType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class FishtrapMenu extends AbstractContainerMenu {
    public final FTItemStackHandler inventory;
    public static final ResourceLocation BAIT_ICON = FishermensTrap.modPrefix("item/empty_slot_bait");

    public FishtrapMenu(int pContainerId, Inventory pPlayerInventory, FTItemStackHandler inventory) {
        super(FTMenuType.FISHTRAP_MENU.get(), pContainerId);
        this.inventory = inventory;

        this.addSlot(new SlotItemHandler(inventory, 0, 81, 15) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(FTItemTags.FISH_BAITS);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(TextureAtlas.LOCATION_BLOCKS, BAIT_ICON);
            }
        });

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new SlotItemHandler(inventory, i1 + 1, 8 + i1 * 18, 48));
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

    public FishtrapMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(windowId, playerInventory, new FTItemStackHandler());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < this.inventory.getItems().size()) {
                if (!this.moveItemStackTo(itemstack1, this.inventory.getItems().size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getItems().size(), false)) {
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
        return true;
    }
}

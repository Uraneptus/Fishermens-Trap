package com.uraneptus.fishermens_trap.common.blocks;

import com.uraneptus.fishermens_trap.core.registry.FTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FishtrapBlockEntity extends RandomizableContainerBlockEntity {

    public FishtrapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FTBlockEntityType.FISHTRAP.get(), pPos, pBlockState);
    }


    @Override
    protected NonNullList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {

    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }
}

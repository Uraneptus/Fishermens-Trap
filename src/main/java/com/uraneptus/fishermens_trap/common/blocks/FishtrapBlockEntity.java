package com.uraneptus.fishermens_trap.common.blocks;

import com.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import com.uraneptus.fishermens_trap.core.registry.FTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraftforge.common.Tags;

import java.util.Objects;

public class FishtrapBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items;
    private static long tickCounter = 0;

    public FishtrapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FTBlockEntityType.FISHTRAP.get(), pPos, pBlockState);
        this.items = NonNullList.withSize(10, ItemStack.EMPTY);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, FishtrapBlockEntity pBlockEntity) {
        RandomSource random = pLevel.getRandom();
        if (tickCounter >= random.nextIntBetweenInclusive(4800, 8000)) {
            tickCounter = 0;
            if (isValidFishingLocation(pLevel, pPos)) {
                LootTable lootTable;
                lootTable = Objects.requireNonNull(pLevel.getServer()).getLootTables().get(BuiltInLootTables.FISHING_JUNK);
                //For adding items to inventory dynamically, look at Hopper way or use ItemStackHandeler
            }
        } else {
            tickCounter++;
        }
    }

    private static boolean isValidFishingLocation(Level pLevel, BlockPos pPos) {
        for (Direction direction : Direction.values()) {
            if (pLevel.getBlockState(pPos).getFluidState().is(FluidTags.WATER)) {
                if (pLevel.getFluidState(pPos.relative(direction)).is(FluidTags.WATER)) {
                    if (pLevel.getBiome(pPos).is(Tags.Biomes.IS_WATER)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        this.items = pItemStacks;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("fishermens_trap.container.fishtrap");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new FishtrapMenu(pContainerId, pInventory, this, this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }
}

package dev.uraneptus.fishermens_trap.common.blocks;

import com.mojang.datafixers.util.Pair;
import dev.uraneptus.fishermens_trap.FishermensTrap;
import dev.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import dev.uraneptus.fishermens_trap.common.tags.FTBiomeTags;
import dev.uraneptus.fishermens_trap.common.tags.FTItemTags;
import dev.uraneptus.fishermens_trap.xplat.FTAbstractions;
import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class FishtrapBlockEntity extends RandomizableContainerBlockEntity {
    public static final Component FISHTRAP_NAME = Component.translatable("fishermens_trap.container.fishtrap");
    private NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    private int tickCounter = 0;

    public FishtrapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FTRegistries.FISHTRAP_BE.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items, pRegistries);
        }
        pTag.putInt("tickCounter", tickCounter);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items, pRegistries);
        }
        this.tickCounter = pTag.getInt("tickCounter");
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        this.unpackLootTable(null);
        return ContainerHelper.removeItem(this.getItems(), slot, amount);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.unpackLootTable(null);
        this.getItems().set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));
    }

    @Override
    protected Component getDefaultName() {
        return FISHTRAP_NAME;
    }

    private CompoundTag saveItems(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.saveAdditional(compound, pRegistries);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.items, pRegistries);
        }
        return compound;
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveItems(new CompoundTag(), pRegistries);
    }

    public static Pair<Integer, Integer> getMinMaxCounterInts() {
        if (FishermensTrap.ABSTRACTIONS.isDevEnvironment()) {
            return Pair.of(48, 80);
        }
        return Pair.of(FishermensTrap.CONFIG.minTicksToFish(), FishermensTrap.CONFIG.maxTicksToFish());
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, FishtrapBlockEntity pBlockEntity) {
        RandomSource random = pLevel.getRandom();
        if (getMinMaxCounterInts().getSecond() > getMinMaxCounterInts().getFirst()) {
            if (pBlockEntity.tickCounter >= random.nextIntBetweenInclusive(getMinMaxCounterInts().getFirst(), getMinMaxCounterInts().getSecond())) {
                pBlockEntity.tickCounter = 0;
                if (isValidFishingLocation(pLevel, pPos)) {
                    LootParams lootparams = (new LootParams.Builder((ServerLevel)pLevel)).create(LootContextParamSets.EMPTY);
                    ItemStack itemInBaitSlot = pBlockEntity.getItem(0);
                    LootTable loottable;

                    if (itemInBaitSlot.is(FTItemTags.FISH_BAIT) && !itemInBaitSlot.is(Items.AIR)) {
                        ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(itemInBaitSlot.getItem());
                        ResourceLocation lootTableLocation = FishermensTrap.modPrefix("gameplay/fishtrap_fishing/" + Objects.requireNonNull(registryName).getNamespace() + "/" + registryName.getPath());
                        loottable = pLevel.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableLocation));
                    } else {
                        loottable = pLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING_JUNK);
                    }
                    List<ItemStack> list = loottable.getRandomItems(lootparams);
                    //Prevent Fd slices from being collected by trap
                    list.forEach(item -> {
                        String name = BuiltInRegistries.ITEM.getKey(item.getItem()).getPath();
                        if (name.contains("_slice")) {
                            list.remove(item);
                        }
                    });
                    FishtrapFillingBehavior.handleItemInsertion(pBlockEntity, list, itemInBaitSlot, random);
                }
            } else {
                pBlockEntity.tickCounter++;
            }
        } else {
            FishermensTrap.LOGGER.error("Fish trap ticks: [Min value must be below Max value]");
        }
    }

    private static boolean isValidFishingLocation(Level pLevel, BlockPos pPos) {
        for (Direction direction : Direction.values()) {
            if (pLevel.getBlockState(pPos).getFluidState().is(FluidTags.WATER)) {
                if (pLevel.getFluidState(pPos.relative(direction)).is(FluidTags.WATER)) {
                    if (pLevel.getBiome(pPos).is(FTBiomeTags.CAN_FISHTRAP_FISH)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return (slot == 0 && stack.is(FTItemTags.FISH_BAIT)) || stack.is(Items.WATER_BUCKET);
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return slot != 0 && !stack.is(Items.WATER_BUCKET);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FishtrapMenu(containerId, inventory, ContainerLevelAccess.NULL, this);
    }
}
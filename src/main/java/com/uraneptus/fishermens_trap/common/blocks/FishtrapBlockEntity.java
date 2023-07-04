package com.uraneptus.fishermens_trap.common.blocks;

import com.google.common.collect.ImmutableList;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.common.FTFishingData;
import com.uraneptus.fishermens_trap.common.FishingDataReloadListener;
import com.uraneptus.fishermens_trap.common.blocks.container.FTItemStackHandler;
import com.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishtrapBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    public static final Component FISHTRAP_NAME = Component.translatable("fishermens_trap.container.fishtrap");
    private final FTItemStackHandler handler = new FTItemStackHandler();
    private final LazyOptional<IItemHandler> input = LazyOptional.of(() -> new RangedWrapper(this.handler, 0, 0));
    private final LazyOptional<IItemHandler> output = LazyOptional.of(() -> new RangedWrapper(this.handler, 1, 9));
    private int tickCounter = 0;

    public FishtrapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FTBlockEntityType.FISHTRAP.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("tickCounter", tickCounter);
        ContainerHelper.saveAllItems(pTag, this.handler.getItems());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.tickCounter = pTag.getInt("tickCounter");
        ContainerHelper.loadAllItems(pTag, this.handler.getItems());
        this.handler.deserializeNBT(pTag);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, FishtrapBlockEntity pBlockEntity) {
        RandomSource random = pLevel.getRandom();
        if (pBlockEntity.tickCounter >= random.nextIntBetweenInclusive(48, 80)) {
            pBlockEntity.tickCounter = 0;
            if (isValidFishingLocation(pLevel, pPos)) {
                LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)pLevel))
                        .withParameter(LootContextParams.ORIGIN, new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()))
                        .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                        .withParameter(LootContextParams.BLOCK_ENTITY, pBlockEntity)
                        .withRandom(random);
                LootTable loottable;
                ItemStack itemInBaitSlot = pBlockEntity.handler.getStackInSlot(0);
                /*
                if (itemInBaitSlot.is(FTItemTags.ANY_FISH)) {
                    loottable = pLevel.getServer().getLootTables().get(BuiltInLootTables.FISHING_FISH);
                } else if (itemInBaitSlot.is(FTItemTags.SALMON)) {
                    loottable = pLevel.getServer().getLootTables().get(FishermensTrap.modPrefix("gameplay/fishtraps/salmon"));
                } else if (itemInBaitSlot.is(FTItemTags.COD)) {
                    loottable = pLevel.getServer().getLootTables().get(FishermensTrap.modPrefix("gameplay/fishtraps/cod"));
                } else if (itemInBaitSlot.is(FTItemTags.PUFFERFISH)) {
                    loottable = pLevel.getServer().getLootTables().get(FishermensTrap.modPrefix("gameplay/fishtraps/pufferfish"));
                } else if (itemInBaitSlot.is(FTItemTags.TROPICAL_FISH)) {
                    loottable = pLevel.getServer().getLootTables().get(FishermensTrap.modPrefix("gameplay/fishtraps/tropical_fish"));
                } else {
                    loottable = pLevel.getServer().getLootTables().get(BuiltInLootTables.FISHING_JUNK);
                }

                 */
                System.out.println("valid location");
                List<FTFishingData> data = FishingDataReloadListener.FISHING_DATA.get(ForgeRegistries.ITEMS.getKey(itemInBaitSlot.getItem()));
                System.out.println(data);
                if (data != null) {
                    System.out.println("data is not null");
                    for (FTFishingData resultData : data) {
                        //if (random.nextFloat() < resultData.chance) {
                            System.out.println("random smaller than chance");
                            //TODO get copy of result item
                            pBlockEntity.handler.addItemsToInventory(Arrays.stream(resultData.result.getItems()).toList());
                            System.out.println("items added");
                        //}
                    }
                }
                if (!itemInBaitSlot.isEmpty()) {
                    itemInBaitSlot.shrink(1);
                }
            }

        } else {
            pBlockEntity.tickCounter++;
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
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            if (side != null) {
                return side.equals(Direction.UP) ? input.cast() : side.equals(Direction.DOWN) ? output.cast() : LazyOptional.empty();
            }
        }
        return super.getCapability(cap, side);
    }

    public FTItemStackHandler getInventory() {
        return this.handler;
    }

    @Override
    public Component getName() {
        return FISHTRAP_NAME;
    }

    @Override
    public Component getDisplayName() {
        return FISHTRAP_NAME;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FishtrapMenu(pContainerId, pPlayerInventory, this.handler);
    }
}
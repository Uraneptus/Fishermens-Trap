package com.uraneptus.fishermens_trap.common.blocks;

import com.mojang.datafixers.util.Pair;
import com.uraneptus.fishermens_trap.FTConfig;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.common.blocks.container.FTItemStackHandler;
import com.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import com.uraneptus.fishermens_trap.core.other.FTBiomeTags;
import com.uraneptus.fishermens_trap.core.other.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = FishermensTrap.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FishtrapBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    public static final Component FISHTRAP_NAME = Component.translatable("fishermens_trap.container.fishtrap");
    private final FTItemStackHandler handler = new FTItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private final IItemHandler input = new RangedWrapper(this.handler, 0, 1);
    private final IItemHandler output = new RangedWrapper(this.handler, 1, 10);
    private int tickCounter = 0;

    public FishtrapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FTBlockEntityType.FISHTRAP.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("handler", this.handler.serializeNBT(pRegistries));
        pTag.putInt("tickCounter", tickCounter);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.handler.deserializeNBT(pRegistries, pTag.getCompound("handler"));
        this.tickCounter = pTag.getInt("tickCounter");
    }

    private CompoundTag saveItems(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.saveAdditional(compound, pRegistries);
        compound.put("handler", this.handler.serializeNBT(pRegistries));
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

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.loadAdditional(tag, lookupProvider);
    }

    public static Pair<Integer, Integer> getMinMaxCounterInts() {
        if (!FMLEnvironment.production) {
            return Pair.of(48, 80);
        }
        return Pair.of(FTConfig.MIN_TICKS_TO_FISH.get(), FTConfig.MAX_TICKS_TO_FISH.get());
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, FishtrapBlockEntity pBlockEntity) {
        RandomSource random = pLevel.getRandom();
        if (getMinMaxCounterInts().getSecond() > getMinMaxCounterInts().getFirst()) {
            if (pBlockEntity.tickCounter >= random.nextIntBetweenInclusive(getMinMaxCounterInts().getFirst(), getMinMaxCounterInts().getSecond())) {
                pBlockEntity.tickCounter = 0;
                if (isValidFishingLocation(pLevel, pPos)) {
                    LootParams lootparams = (new LootParams.Builder((ServerLevel)pLevel))
                            .withParameter(LootContextParams.ORIGIN, new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()))
                            .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                            .withParameter(LootContextParams.BLOCK_ENTITY, pBlockEntity)
                            .create(LootContextParamSets.EMPTY);
                    ItemStack itemInBaitSlot = pBlockEntity.handler.getStackInSlot(0);
                    LootTable loottable;

                    if (itemInBaitSlot.is(FTItemTags.FISH_BAITS) && !itemInBaitSlot.is(Items.AIR)) {
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
                    pBlockEntity.handler.handleItemsInsertion(list, itemInBaitSlot, random);
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


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FTBlockEntityType.FISHTRAP.get(),
                (be, context) -> {
                    if (context == Direction.UP) {
                        return be.input;
                    }
                    return be.output;
                }
        );
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
        return new FishtrapMenu(pContainerId, pPlayerInventory, this.handler, ContainerLevelAccess.NULL);
    }
}
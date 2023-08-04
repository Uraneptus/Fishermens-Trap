package com.uraneptus.fishermens_trap.common.blocks;

import com.mojang.datafixers.util.Pair;
import com.uraneptus.fishermens_trap.FTConfig;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.common.blocks.container.FTItemStackHandler;
import com.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import com.uraneptus.fishermens_trap.core.other.tags.FTBiomeTags;
import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class FishtrapBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    public static final Component FISHTRAP_NAME = Component.translatable("fishermens_trap.container.fishtrap");
    private final FTItemStackHandler handler = new FTItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private final LazyOptional<IItemHandler> input = LazyOptional.of(() -> new RangedWrapper(this.handler, 0, 0));
    private final LazyOptional<IItemHandler> output = LazyOptional.of(() -> new RangedWrapper(this.handler, 1, 9));
    private int tickCounter = 0;

    public FishtrapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FTBlockEntityType.FISHTRAP.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("handler", this.handler.serializeNBT());
        pTag.putInt("tickCounter", tickCounter);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.handler.deserializeNBT(pTag.getCompound("handler"));
        this.tickCounter = pTag.getInt("tickCounter");
    }

    private CompoundTag saveItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("handler", this.handler.serializeNBT());
        return compound;
    }

    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveItems(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        this.load(packet.getTag());
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
                    LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)pLevel))
                            .withParameter(LootContextParams.ORIGIN, new Vec3(pPos.getX(), pPos.getY(), pPos.getZ()))
                            .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                            .withParameter(LootContextParams.BLOCK_ENTITY, pBlockEntity)
                            .withRandom(random);
                    ItemStack itemInBaitSlot = pBlockEntity.handler.getStackInSlot(0);
                    LootTable loottable;

                    if (itemInBaitSlot.is(FTItemTags.FISH_BAITS) && !itemInBaitSlot.is(Items.AIR)) {
                        ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(itemInBaitSlot.getItem());
                        ResourceLocation lootTableLocation = FishermensTrap.modPrefix("gameplay/fishtrap_fishing/" + Objects.requireNonNull(registryName).getNamespace() + "/" + registryName.getPath());
                        loottable = pLevel.getServer().getLootTables().get(lootTableLocation);
                    } else {
                        loottable = pLevel.getServer().getLootTables().get(BuiltInLootTables.FISHING_JUNK);
                    }
                    List<ItemStack> list = loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.FISHING));
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

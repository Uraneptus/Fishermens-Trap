package dev.uraneptus.fishermens_trap.xplat;

import dev.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface FTAbstractions {

    boolean isDevEnvironment();

    <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block);
    <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item);
    <B extends BlockEntityType<?>> Supplier<B> registerBlockEntityType(String id, Supplier<B> blockentityType);
    <M extends MenuType<?>> Supplier<M> registerMenu(String id, Supplier<M> menu);

    <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntitySupplier<T> blockEntitySupplier, Block... blocks);
    <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> factory);


    @FunctionalInterface
    interface BlockEntitySupplier<T extends BlockEntity> {
        @NotNull T create(BlockPos pos, BlockState state);
    }

    FTAbstractions INSTANCE = FishermensTrap.loadService(FTAbstractions.class);
}

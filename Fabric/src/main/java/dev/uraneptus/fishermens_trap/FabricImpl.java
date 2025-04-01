package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.xplat.FTAbstractions;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Supplier;

public class FabricImpl implements FTAbstractions {

    @Override
    public boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        return () -> Registry.register(BuiltInRegistries.BLOCK, FishermensTrap.modPrefix(id), block.get());
    }

    @Override
    public <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        return () -> Registry.register(BuiltInRegistries.ITEM, FishermensTrap.modPrefix(id), item.get());
    }

    @Override
    public <B extends BlockEntityType<?>> Supplier<B> registerBlockEntityType(String id, Supplier<B> blockentityType) {
        return () -> Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishermensTrap.modPrefix(id), blockentityType.get());
    }

    @Override
    public <M extends MenuType<?>> Supplier<M> registerMenu(String id, Supplier<M> menu) {
        return () -> Registry.register(BuiltInRegistries.MENU, FishermensTrap.modPrefix(id), menu.get());
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(FTAbstractions.BlockEntitySupplier<T> blockEntitySupplier, Block... blocks) {
        return BlockEntityType.Builder.of(blockEntitySupplier::create, blocks).build(null);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> factory) {
        return FabricMenuTypeExtension.create(factory::apply);
    }
}

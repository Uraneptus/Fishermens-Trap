package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.xplat.FTAbstractions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Supplier;

public class FTNeoforgeImpl implements FTAbstractions {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, FishermensTrap.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, FishermensTrap.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FishermensTrap.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(BuiltInRegistries.MENU, FishermensTrap.MOD_ID);

    @Override
    public boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }

    @Override
    public <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        return BLOCKS.register(id, block);
    }

    @Override
    public <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        return ITEMS.register(id, item);
    }

    @Override
    public <B extends BlockEntityType<?>> Supplier<B> registerBlockEntityType(String id, Supplier<B> blockentityType) {
        return BLOCK_ENTITIES.register(id, blockentityType);
    }

    @Override
    public <M extends MenuType<?>> Supplier<M> registerMenu(String id, Supplier<M> menu) {
        return MENU.register(id, menu);
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntity(FTAbstractions.BlockEntitySupplier<T> blockEntitySupplier, Block... blocks) {
        return BlockEntityType.Builder.of(blockEntitySupplier::create, blocks).build(null);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> factory) {
        return IMenuTypeExtension.create(factory::apply);
    }
}

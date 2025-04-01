package dev.uraneptus.fishermens_trap.common.registry;

import dev.uraneptus.fishermens_trap.FishermensTrap;
import dev.uraneptus.fishermens_trap.common.blocks.FishtrapBlock;
import dev.uraneptus.fishermens_trap.common.blocks.FishtrapBlockEntity;
import dev.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import dev.uraneptus.fishermens_trap.xplat.FTAbstractions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FTRegistries {
    private static final FTAbstractions REG = FishermensTrap.ABSTRACTIONS;
    public static Map<ResourceKey<CreativeModeTab>, List<ItemLike>> TAB_ENTRIES = new HashMap<>();

    public static void init() {}

    public static final Supplier<FishtrapBlock> FISHTRAP_BLOCK = REG.registerBlock("fishtrap", () -> new FishtrapBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).mapColor(MapColor.COLOR_BROWN).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<BlockItem> FISHTRAP_ITEM = REG.registerItem("fishtrap", () -> new BlockItem(FISHTRAP_BLOCK.get(), new Item.Properties()));
    public static final Supplier<BlockEntityType<FishtrapBlockEntity>> FISHTRAP_BE = REG.registerBlockEntityType("fishtrap_block_entity", () -> REG.createBlockEntity(FishtrapBlockEntity::new, FISHTRAP_BLOCK.get()));
    public static final Supplier<MenuType<FishtrapMenu>> FISHTRAP_MENU = REG.registerMenu("fishtrap_menu", () -> REG.createMenuType(FishtrapMenu::new));

    public static void initTabEntries() {
        TAB_ENTRIES.put(CreativeModeTabs.FUNCTIONAL_BLOCKS, List.of(FISHTRAP_ITEM.get()));
    }
}

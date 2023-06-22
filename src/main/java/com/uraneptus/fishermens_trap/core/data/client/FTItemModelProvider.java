package com.uraneptus.fishermens_trap.core.data.client;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.registry.FTBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.uraneptus.fishermens_trap.core.data.FTDatagenUtil.*;

@SuppressWarnings({"unused", "SameParameterValue"})
public class FTItemModelProvider extends ItemModelProvider {

    public FTItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, FishermensTrap.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicBlockItem(FTBlocks.FISHTRAP);
    }

    private void basicBlockItem(Supplier<? extends Block> blockForItem) {
        withExistingParent(name(blockForItem.get()), modBlockLocation(name(blockForItem.get())));
    }

    private void basicItem(Supplier<? extends Item> item) {
        basicItem(item.get());
    }

    private void blockItemWithBlockTexture(Supplier<? extends Block> blockForItem) {
        withExistingParent(name(blockForItem.get()), GENERATED).texture(LAYER0, modBlockLocation(name(blockForItem.get())));
    }

    private void blockItemWithItemTexture(Supplier<? extends Block> blockForItem) {
        basicItem(blockForItem.get().asItem());
    }
}

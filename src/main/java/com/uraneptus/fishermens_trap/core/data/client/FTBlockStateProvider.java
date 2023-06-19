package com.uraneptus.fishermens_trap.core.data.client;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class FTBlockStateProvider extends BlockStateProvider {
    public FTBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FishermensTrap.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

    private void basicBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
    }

}

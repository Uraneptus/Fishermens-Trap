package com.uraneptus.fishermens_trap.core.data.client;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.common.blocks.FishtrapBlock;
import com.uraneptus.fishermens_trap.core.registry.FTBlocks;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.uraneptus.fishermens_trap.core.data.FTDatagenUtil.name;

@SuppressWarnings("SameParameterValue")
public class FTBlockStateProvider extends BlockStateProvider {
    public FTBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FishermensTrap.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        fishtrap(FTBlocks.FISHTRAP);
    }

    private void basicBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
    }

    private void fishtrap(Supplier<? extends Block> block) {

        ModelFile fishtrap = models().getBuilder(name(block.get()))
                .texture("front", "fishermens_trap:block/fishtrap_front")
                .texture("side", "fishermens_trap:block/fishtrap_side")
                .texture("bottom", "fishermens_trap:block/fishtrap_bottom")
                .texture("top", "fishermens_trap:block/fishtrap_top")
                .texture("handles", "fishermens_trap:block/fishtrap_handles")
                .texture("particle", "fishermens_trap:block/fishtrap_front")
                .element().from(0, 0, 3).to(16, 10, 13)
                .face(Direction.NORTH).uvs(0, 3, 16, 13).texture("#front").end()
                .face(Direction.EAST).uvs(3, 3, 13, 13).texture("#side").end()
                .face(Direction.SOUTH).uvs(0, 3, 16, 13).texture("#front").end()
                .face(Direction.WEST).uvs(3, 3, 13, 13).texture("#side").end()
                .face(Direction.UP).uvs(16, 13, 0, 3).texture("#top").end()
                .face(Direction.DOWN).uvs(16, 3, 0, 13).texture("#bottom").end()
                .end().element().from(2, 10, 8).to(14, 16, 8)
                .face(Direction.NORTH).uvs(2, 7, 14, 13).texture("#handles").end()
                .face(Direction.EAST).uvs(2, 7, 14, 13).texture("#handles").end()
                .face(Direction.SOUTH).uvs(2, 7, 14, 13).texture("#handles").end()
                .face(Direction.WEST).uvs(14, 7, 2, 13).texture("#handles").end()
                .face(Direction.UP).uvs(14, 13, 2, 7).texture("#handles").end()
                .face(Direction.DOWN).uvs(14, 7, 2, 13).texture("#handles").end()
                .end().transforms()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(75, 45, 0).translation(0, 2.5F, 0).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(75, 45, 0).translation(0, 2.5F, 0).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND).rotation(0, 45, 0).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND).rotation(0, 225, 0).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemTransforms.TransformType.GROUND).translation(0, 3, 0).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemTransforms.TransformType.GUI).rotation(30, 225, 0).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemTransforms.TransformType.FIXED).scale(0.5F, 0.5F, 0.5F).end()
                .end().renderType("cutout");

        ModelFile hangingFishtrap = models().getBuilder(name(block.get()) + "_hanging")
                .texture("front", "fishermens_trap:block/fishtrap_front")
                .texture("side", "fishermens_trap:block/fishtrap_side")
                .texture("bottom", "fishermens_trap:block/fishtrap_bottom")
                .texture("top", "fishermens_trap:block/fishtrap_top")
                .texture("handles", "fishermens_trap:block/fishtrap_handles")
                .texture("particle", "fishermens_trap:block/fishtrap_front")
                .element().from(0, 0, 3).to(16, 10, 13)
                .face(Direction.NORTH).uvs(0, 3, 16, 13).texture("#front").end()
                .face(Direction.EAST).uvs(3, 3, 13, 13).texture("#side").end()
                .face(Direction.SOUTH).uvs(0, 3, 16, 13).texture("#front").end()
                .face(Direction.WEST).uvs(3, 3, 13, 13).texture("#side").end()
                .face(Direction.UP).uvs(16, 13, 0, 3).texture("#top").end()
                .face(Direction.DOWN).uvs(16, 3, 0, 13).texture("#bottom").end()
                .end().element().from(2, 10, 8).to(14, 16, 8)
                .face(Direction.NORTH).uvs(2, 1, 14, 7).texture("#handles").end()
                .face(Direction.EAST).uvs(2, 1, 14, 7).texture("#handles").end()
                .face(Direction.SOUTH).uvs(2, 1, 14, 7).texture("#handles").end()
                .face(Direction.WEST).uvs(14, 1, 2, 7).texture("#handles").end()
                .face(Direction.UP).uvs(14, 7, 2, 1).texture("#handles").end()
                .face(Direction.DOWN).uvs(14, 1, 2, 7).texture("#handles").end()
                .end().renderType("cutout");

        getVariantBuilder(block.get()).forAllStates(blockState -> {

            if (blockState.getValue(FishtrapBlock.HANGING)) {
                return ConfiguredModel.builder().modelFile(hangingFishtrap).rotationY(((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
            }

            return ConfiguredModel.builder().modelFile(fishtrap).rotationY(((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });

    }
}

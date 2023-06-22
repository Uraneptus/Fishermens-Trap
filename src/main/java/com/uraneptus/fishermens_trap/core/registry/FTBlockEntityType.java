package com.uraneptus.fishermens_trap.core.registry;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.common.blocks.FishtrapBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTBlockEntityType {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FishermensTrap.MOD_ID);

    public static final RegistryObject<BlockEntityType<FishtrapBlockEntity>> FISHTRAP = BLOCK_ENTITY_TYPE.register("fishtrap", () -> BlockEntityType.Builder.of(FishtrapBlockEntity::new, new Block[]{FTBlocks.FISHTRAP.get()}).build(null));
}

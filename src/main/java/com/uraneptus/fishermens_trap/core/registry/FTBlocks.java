package com.uraneptus.fishermens_trap.core.registry;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FishermensTrap.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FishermensTrap.MOD_ID);


}

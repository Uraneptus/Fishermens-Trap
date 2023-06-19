package com.uraneptus.fishermens_trap.core.registry;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FishermensTrap.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FishermensTrap.MOD_ID);


}

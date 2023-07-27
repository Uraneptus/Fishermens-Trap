package com.uraneptus.fishermens_trap.integration;

import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FarmersDelight {
    public static final String FD_MODID = "farmersdelight";

    public static Item getCanvas() {
        return ModItems.CANVAS.get();
    }
}

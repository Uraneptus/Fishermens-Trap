package com.uraneptus.fishermens_trap.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FTFishingData {
    public final Ingredient result;
    public final int weight;

    public FTFishingData(Ingredient result, int weight) {
        this.result = result;
        this.weight = weight;
    }
}

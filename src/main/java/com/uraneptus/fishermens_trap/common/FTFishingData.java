package com.uraneptus.fishermens_trap.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FTFishingData {
    public final Ingredient result;
    public final float chance;

    public FTFishingData(Ingredient result, float chance) {
        this.result = result;
        this.chance = chance;
    }
}

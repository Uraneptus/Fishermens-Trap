package com.uraneptus.fishermens_trap.core.data.server;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

@SuppressWarnings("SameParameterValue")
public class FTRecipeProvider extends RecipeProvider {

    public FTRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {


    }

}

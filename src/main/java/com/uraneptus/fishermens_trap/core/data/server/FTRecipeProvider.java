package com.uraneptus.fishermens_trap.core.data.server;

import com.uraneptus.fishermens_trap.integration.FarmersDelight;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class FTRecipeProvider extends RecipeProvider {

    public FTRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        fishtrapRecipe(FarmersDelight.CANVAS, Items.STRING, FTItems.FISHTRAP, consumer);
    }

    private static void fishtrapRecipe(ItemLike FDIngredient, ItemLike vanillaIngredient, Supplier<? extends ItemLike> result, Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(FarmersDelight.FD_MODID))
                .addRecipe(consumer1 -> fishtrapShapedBuilder(FDIngredient, result, consumer1))
                .build(consumer, FishermensTrap.modPrefix(getItemName(result.get()) + "_" + FarmersDelight.FD_MODID));

        ConditionalRecipe.builder()
                .addCondition(new NotCondition(new ModLoadedCondition(FarmersDelight.FD_MODID)))
                .addRecipe(consumer1 -> fishtrapShapedBuilder(vanillaIngredient, result, consumer1))
                .build(consumer, FishermensTrap.modPrefix(getItemName(result.get())));

    }

    private static void fishtrapShapedBuilder(ItemLike exchangeableIngredient, Supplier<? extends ItemLike> result, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result.get(), 1).define('X', Items.STICK).define('#', exchangeableIngredient)
                .pattern("X#X")
                .pattern("###")
                .pattern("X#X")
                .unlockedBy(getHasName(exchangeableIngredient), has(exchangeableIngredient))
                .save(consumer, FishermensTrap.modPrefix(getItemName(result.get())));
    }


}

package com.uraneptus.fishermens_trap.integration.jei;

import com.google.common.collect.ImmutableList;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.integration.jei.resource.DecompositionDummy;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = FishermensTrap.modPrefix("jei_plugin");
    public static final RecipeType<FishtrapRecipeDummy> FISHTRAP_RECIPE = RecipeType.create(FishermensTrap.MOD_ID, "fishtrap_fishing", FishtrapRecipeDummy.class);

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FishtrapCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(FISHTRAP_RECIPE, ImmutableList.of(new FishtrapRecipeDummy()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(FTItems.FISHTRAP.get()), FISHTRAP_RECIPE);
    }
}

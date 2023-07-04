package com.uraneptus.fishermens_trap.integration.jei;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.client.screen.FishtrapScreen;
import com.uraneptus.fishermens_trap.common.blocks.FishtrapBlockEntity;
import com.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FishtrapCategory implements IRecipeCategory<FishtrapRecipeDummy> {
    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;
    public FishtrapCategory(IGuiHelper helper) {
        this.title = Component.translatable(FishermensTrap.MOD_ID + ".jei." + getUid().getPath());
        this.background = helper.createDrawable(FishtrapScreen.FISHTRAP_LOCATION, 0, 0, 176, 166);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FTItems.FISHTRAP.get()));
    }

    @Override
    public RecipeType<FishtrapRecipeDummy> getRecipeType() {
        return null;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public ResourceLocation getUid() {
        return this.getRecipeType().getUid();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FishtrapRecipeDummy recipe, IFocusGroup focuses) {

    }
}

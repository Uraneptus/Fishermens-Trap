package com.uraneptus.fishermens_trap.integration.jei;

import com.google.common.collect.ImmutableList;
import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FishtrapCategory implements IRecipeCategory<FishtrapRecipeWrapper> {
    private static final ResourceLocation FISHTRAP_LOCATION = FishermensTrap.modPrefix("textures/gui/jei_fishtrap.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;
    public FishtrapCategory(IGuiHelper helper) {
        this.title = Component.translatable(FishermensTrap.MOD_ID + ".jei." + getUid().getPath());
        this.background = helper.createDrawable(FISHTRAP_LOCATION, 0, 0, 162, 51);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FTItems.FISHTRAP.get()));
    }

    @Override
    public RecipeType<FishtrapRecipeWrapper> getRecipeType() {
        return JEIPlugin.FISHTRAP_RECIPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, FishtrapRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 74, 1).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 1, 34).addItemStacks(Arrays.stream(recipe.getOutput().getItems()).toList());
    }

    @Override
    public List<Component> getTooltipStrings(FishtrapRecipeWrapper recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (iconPosition(mouseX, mouseY)) {
            return ImmutableList.of(Component.translatable(FishermensTrap.MOD_ID + ".jei." + getUid().getPath() + ".info"));
        }
        return Collections.emptyList();
    }

    private static boolean iconPosition(double mouseX, double mouseY) {
        int iconPosX = 77;
        int iconPosY = 19;
        int iconHeight = 12;
        int iconWidth = 10;
        return iconPosX <= mouseX && mouseX < iconPosX + iconWidth && iconPosY <= mouseY && mouseY < iconPosY + iconHeight;
    }

}

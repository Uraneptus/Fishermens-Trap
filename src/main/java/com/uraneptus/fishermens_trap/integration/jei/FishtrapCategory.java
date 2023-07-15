package com.uraneptus.fishermens_trap.integration.jei;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.client.screen.FishtrapScreen;
import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class FishtrapCategory implements IRecipeCategory<FishtrapRecipeDummy> {
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
    public RecipeType<FishtrapRecipeDummy> getRecipeType() {
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
    public void setRecipe(IRecipeLayoutBuilder builder, FishtrapRecipeDummy recipe, IFocusGroup focuses) {
        Map<ItemStack, List<ItemStack>> baitResults = new HashMap<>();
        List<ItemStack> allBaits = ForgeRegistries.ITEMS.tags().getTag(FTItemTags.FISH_BAITS).stream().map(ItemStack::new).toList();

        for (ItemStack bait : allBaits) {
            ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(bait.getItem());
            ResourceLocation resultTagLocation = FishermensTrap.modPrefix("jei_display_results/" + registryName.getNamespace() + "/" + registryName.getPath());
            TagKey<Item> resultTag = ForgeRegistries.ITEMS.tags().createTagKey(resultTagLocation);

            baitResults.put(bait, ForgeRegistries.ITEMS.tags().getTag(resultTag).stream().map(ItemStack::new).toList());

        }
        System.out.println(baitResults);

        builder.addSlot(RecipeIngredientRole.INPUT, 74, 1).addItemStacks(baitResults.keySet().stream().toList());
        baitResults.forEach((itemStack, itemStacks) -> {

            for (int i = 0; i < itemStacks.size(); i++) {
                builder.addSlot(RecipeIngredientRole.OUTPUT, 1 + i * 18 , 34).addItemStacks(itemStacks);
            }
        });


    }
}

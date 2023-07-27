package com.uraneptus.fishermens_trap.integration.jei;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.other.FTItemTags;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = FishermensTrap.modPrefix("jei_plugin");
    public static final RecipeType<FishtrapRecipeWrapper> FISHTRAP_RECIPE = RecipeType.create(FishermensTrap.MOD_ID, "fishtrap_fishing", FishtrapRecipeWrapper.class);

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FishtrapCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(FISHTRAP_RECIPE, addWrappers());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(FTItems.FISHTRAP.get()), FISHTRAP_RECIPE);
    }

    public List<FishtrapRecipeWrapper> addWrappers() {
        List<FishtrapRecipeWrapper> list = new ArrayList<>();
        for (ItemStack item : ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).toList()) {
            if (item.is(FTItemTags.FISH_BAITS)) {
                ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item.getItem());
                TagKey<Item> outputTag = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/" + registryName.getNamespace() + "/" + registryName.getPath()));
                if (ForgeRegistries.ITEMS.tags().isKnownTagName(outputTag)) {
                    list.add(new FishtrapRecipeWrapper(item, Ingredient.of(outputTag)));
                }

            }
        }
        return list;
    }
}

package com.uraneptus.fishermens_trap.common;

import com.google.gson.*;
import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishingDataReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).create();
    public static Map<ResourceLocation, List<FTFishingData>> FISHING_DATA = new HashMap<>();

    public FishingDataReloadListener() {
        super(GSON, "fishing_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FISHING_DATA.clear();
        for (int i = 0; i < pObject.size(); i++) {
            ResourceLocation location = (ResourceLocation) pObject.keySet().toArray()[i];
            JsonObject object = pObject.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            ResourceLocation resourceLocation = new ResourceLocation(name);
            if (!ForgeRegistries.ITEMS.containsKey(resourceLocation)) {
                continue;
            }
            if (FISHING_DATA.containsKey(resourceLocation)) {
                FishermensTrap.LOGGER.info("item with registry name: " + name + " already has fishing data associated with it. Overwriting.");
            }
            JsonArray drops = object.getAsJsonArray("drops");
            List<FTFishingData> dropsList = new ArrayList<>();
            for (JsonElement drop : drops) {
                JsonObject dropObject = drop.getAsJsonObject();
                if (!dropObject.has("result")) {
                    FishermensTrap.LOGGER.info("item with registry name: " + name + " lacks a fishing result. Skipping drops entry.");
                    continue;
                }
                Ingredient dropIngredient = Ingredient.fromJson(dropObject.getAsJsonObject("result"));
                float chance = dropObject.getAsJsonPrimitive("chance").getAsFloat();
                dropsList.add(new FTFishingData(dropIngredient, chance));
            }
            FISHING_DATA.put(resourceLocation, dropsList);
        }
    }
}

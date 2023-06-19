package com.uraneptus.fishermens_trap.core.data;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class FTDatagenUtil {
    public static final String LAYER0 = "layer0";
    public static final String CROSS = "cross";
    public static final String ALL = "all";
    public static final ResourceLocation GENERATED = vanillaItemLocation("generated");
    public static final ResourceLocation HANDHELD = vanillaItemLocation("handheld");

    public static String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public static String name(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public static ResourceLocation modBlockLocation(String path) {
        return FishermensTrap.modPrefix(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation modItemLocation(String path) {
        return FishermensTrap.modPrefix(ModelProvider.ITEM_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaBlockLocation(String path) {
        return new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaItemLocation(String path) {
        return new ResourceLocation(ModelProvider.ITEM_FOLDER + "/" + path);
    }
}

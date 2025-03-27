package dev.uraneptus.fishermens_trap.data;

import dev.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelProvider;

@SuppressWarnings("unused")
public class FTDatagenUtil {
    public static final String LAYER0 = "layer0";
    public static final String CROSS = "cross";
    public static final String ALL = "all";
    public static final ResourceLocation GENERATED = vanillaItemLocation("generated");
    public static final ResourceLocation HANDHELD = vanillaItemLocation("handheld");

    public static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public static String name(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public static ResourceLocation modBlockLocation(String path) {
        return FishermensTrap.modPrefix(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation modItemLocation(String path) {
        return FishermensTrap.modPrefix(ModelProvider.ITEM_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaBlockLocation(String path) {
        return ResourceLocation.withDefaultNamespace(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaItemLocation(String path) {
        return ResourceLocation.withDefaultNamespace(ModelProvider.ITEM_FOLDER + "/" + path);
    }
}

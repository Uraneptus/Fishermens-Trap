package com.uraneptus.fishermens_trap.core.other.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FTItemTags {
    public static final TagKey<Item> FISH_BAITS = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits"));
    public static final TagKey<Item> BEETROOT = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("jei_display_results/minecraft/beetroot"));
    public static final TagKey<Item> BREAD = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("jei_display_results/minecraft/bread"));
    public static final TagKey<Item> GOLDEN_CARROT = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("jei_display_results/minecraft/golden_carrot"));
    public static final TagKey<Item> MELON_SLICE = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("jei_display_results/minecraft/melon_slice"));
    public static final TagKey<Item> SWEET_BERRIES = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("jei_display_results/minecraft/sweet_berries"));
    public static final TagKey<Item> AIR = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("jei_display_results/minecraft/air"));

}

package com.uraneptus.fishermens_trap.core.other;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FTItemTags {
    public static final TagKey<Item> FISH_BAITS = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("fish_baits"));
    public static final TagKey<Item> BEETROOT = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/minecraft/beetroot"));
    public static final TagKey<Item> BREAD = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/minecraft/bread"));
    public static final TagKey<Item> GOLDEN_CARROT = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/minecraft/golden_carrot"));
    public static final TagKey<Item> MELON_SLICE = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/minecraft/melon_slice"));
    public static final TagKey<Item> SWEET_BERRIES = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/minecraft/sweet_berries"));
    public static final TagKey<Item> AIR = TagKey.create(Registries.ITEM, FishermensTrap.modPrefix("jei_display_results/minecraft/air"));

}

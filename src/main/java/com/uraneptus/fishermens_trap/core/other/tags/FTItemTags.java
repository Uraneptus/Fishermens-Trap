package com.uraneptus.fishermens_trap.core.other.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FTItemTags {
    public static final TagKey<Item> FISH_BAITS = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits"));
    public static final TagKey<Item> ANY_FISH = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits/any_fish"));
    public static final TagKey<Item> PUFFERFISH = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits/pufferfish"));
    public static final TagKey<Item> COD = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits/cod"));
    public static final TagKey<Item> SALMON = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits/salmon"));
    public static final TagKey<Item> TROPICAL_FISH = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits/tropical_fish"));

}

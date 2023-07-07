package com.uraneptus.fishermens_trap.core.other.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FTItemTags {
    public static final TagKey<Item> FISH_BAITS = TagKey.create(Registry.ITEM_REGISTRY, FishermensTrap.modPrefix("fish_baits"));

}

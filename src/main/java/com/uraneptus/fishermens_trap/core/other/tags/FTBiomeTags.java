package com.uraneptus.fishermens_trap.core.other.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FTBiomeTags {
    public static final TagKey<Biome> CAN_FISHTRAP_FISH = TagKey.create(Registry.BIOME_REGISTRY, FishermensTrap.modPrefix("can_fishtrap_fish"));
}

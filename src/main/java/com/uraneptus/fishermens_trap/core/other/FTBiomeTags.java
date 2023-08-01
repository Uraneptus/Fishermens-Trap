package com.uraneptus.fishermens_trap.core.other;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FTBiomeTags {
    public static final TagKey<Biome> CAN_FISHTRAP_FISH = TagKey.create(Registries.BIOME, FishermensTrap.modPrefix("can_fishtrap_fish"));

}

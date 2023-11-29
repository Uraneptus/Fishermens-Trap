package com.uraneptus.fishermens_trap.core.data.server.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.other.tags.FTBiomeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class FTBiomeTagsProvider extends BiomeTagsProvider {
    public FTBiomeTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper fileHelper) {
        super(generator, FishermensTrap.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags() {
        tag(FTBiomeTags.CAN_FISHTRAP_FISH).addTag(Tags.Biomes.IS_WATER).addTag(BiomeTags.IS_BEACH).addTag(Tags.Biomes.IS_SWAMP);
    }
}

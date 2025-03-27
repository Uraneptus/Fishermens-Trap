package dev.uraneptus.fishermens_trap.data.server.tags;

import dev.uraneptus.fishermens_trap.FishermensTrap;
import dev.uraneptus.fishermens_trap.common.tags.FTBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FTBiomeTagsProvider extends BiomeTagsProvider {
    public FTBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, FishermensTrap.MOD_ID, existingFileHelper);
    }

    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FTBiomeTags.CAN_FISHTRAP_FISH).addTag(Tags.Biomes.IS_AQUATIC).addTag(BiomeTags.IS_BEACH).addTag(Tags.Biomes.IS_SWAMP);
    }

}

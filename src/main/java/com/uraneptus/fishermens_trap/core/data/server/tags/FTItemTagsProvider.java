package com.uraneptus.fishermens_trap.core.data.server.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.other.tags.FTItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class FTItemTagsProvider extends ItemTagsProvider {

    public FTItemTagsProvider(DataGenerator generator, BlockTagsProvider provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, provider, FishermensTrap.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(FTItemTags.ANY_FISH).add(Items.BREAD);
        tag(FTItemTags.PUFFERFISH).add(Items.GOLDEN_CARROT);
        tag(FTItemTags.COD).add(Items.BEETROOT);
        tag(FTItemTags.SALMON).add(Items.SWEET_BERRIES);
        tag(FTItemTags.TROPICAL_FISH).add(Items.MELON_SLICE);

        tag(FTItemTags.FISH_BAITS)
                .addTag(FTItemTags.ANY_FISH)
                .addTag(FTItemTags.PUFFERFISH)
                .addTag(FTItemTags.COD)
                .addTag(FTItemTags.TROPICAL_FISH)
                .addTag(FTItemTags.SALMON);
    }
}

package com.uraneptus.fishermens_trap.core.data.server.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class FTItemTagsProvider extends ItemTagsProvider {

    public FTItemTagsProvider(DataGenerator generator, BlockTagsProvider provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, provider, FishermensTrap.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }
}

package com.uraneptus.fishermens_trap.core.data.server.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class FTBlockTagsProvider extends BlockTagsProvider {

    public FTBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, FishermensTrap.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags() {

    }
}

package com.uraneptus.fishermens_trap.core.data.server.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class FTBiomeTagsProvider extends BiomeTagsProvider {
    public FTBiomeTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper fileHelper) {
        super(generator, FishermensTrap.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags() {

    }
}

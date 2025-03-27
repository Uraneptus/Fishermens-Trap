package dev.uraneptus.fishermens_trap.data.client;

import dev.uraneptus.fishermens_trap.FishermensTrap;
import dev.uraneptus.fishermens_trap.common.blocks.container.FishtrapBaitSlot;
import dev.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FTSpriteSourceProvider extends SpriteSourceProvider {

    public FTSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        super(output, lookupProvider, FishermensTrap.MOD_ID, fileHelper);
    }

    @Override
    protected void gather() {
        atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(FishtrapBaitSlot.BAIT_ICON, Optional.empty()));
    }
}

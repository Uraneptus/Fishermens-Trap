package com.uraneptus.fishermens_trap.core.data.server.tags;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.core.other.FTItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FTItemTagsProvider extends ItemTagsProvider {

    public FTItemTagsProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> pProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, pProvider, blockProvider, FishermensTrap.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FTItemTags.FISH_BAITS)
                .add(Items.BREAD)
                .add(Items.GOLDEN_CARROT)
                .add(Items.BEETROOT)
                .add(Items.SWEET_BERRIES)
                .add(Items.MELON_SLICE)
                .add(Items.AIR);

        tag(FTItemTags.GOLDEN_CARROT).add(Items.PUFFERFISH);
        tag(FTItemTags.SWEET_BERRIES).add(Items.SALMON);
        tag(FTItemTags.MELON_SLICE).add(Items.TROPICAL_FISH);
        tag(FTItemTags.BEETROOT).add(Items.COD);
        tag(FTItemTags.BREAD).add(Items.COD).add(Items.SALMON).add(Items.TROPICAL_FISH).add(Items.PUFFERFISH);
        tag(FTItemTags.AIR)
                .add(Items.LILY_PAD)
                .add(Items.LEATHER_BOOTS)
                .add(Items.LEATHER)
                .add(Items.BONE)
                .add(Items.POTION)
                .add(Items.STRING)
                .add(Items.FISHING_ROD)
                .add(Items.BOWL)
                .add(Items.STICK)
                .add(Items.INK_SAC)
                .add(Items.TRIPWIRE_HOOK);
    }
}

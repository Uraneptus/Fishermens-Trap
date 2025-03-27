package dev.uraneptus.fishermens_trap.data.server.loot;

import dev.uraneptus.fishermens_trap.FTNeoforgeImpl;
import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class FTBlockLoot extends BlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

    protected FTBlockLoot(HolderLookup.Provider provider) {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FTNeoforgeImpl.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }

    @Override
    protected void generate() {
        this.dropSelf(FTRegistries.FISHTRAP_BLOCK.get());
    }
}

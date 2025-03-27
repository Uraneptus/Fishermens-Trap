package dev.uraneptus.fishermens_trap.data.server.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FTLootTablesProvider extends LootTableProvider {

    public FTLootTablesProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pOutput, Collections.emptySet(), List.of(
                new SubProviderEntry(FTBlockLoot::new, LootContextParamSets.BLOCK)
        ), lookupProvider);
    }
}

package com.uraneptus.fishermens_trap.core.data.server.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FTLootTablesProvider extends LootTableProvider {

    public FTLootTablesProvider(PackOutput pOutput) {
        super(pOutput, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(FTBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}

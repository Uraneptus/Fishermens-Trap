package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import dev.uraneptus.fishermens_trap.config.FTConfigFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;

public class FishermensTrapFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FishermensTrap.init();
        FTRegistries.initTabEntries();
        FTConfigFabric.setup();
        addToItemGroup();
    }

    private static void addToItemGroup() {
        FTRegistries.TAB_ENTRIES.forEach((tab, itemList) -> {
            ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> {
                for (ItemLike item : itemList) {
                    entries.accept(item);
                }
            });
        });
    }
}

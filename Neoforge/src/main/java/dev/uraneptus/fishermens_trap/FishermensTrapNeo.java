package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import dev.uraneptus.fishermens_trap.data.client.FTBlockStateProvider;
import dev.uraneptus.fishermens_trap.data.client.FTItemModelProvider;
import dev.uraneptus.fishermens_trap.data.client.FTLangProvider;
import dev.uraneptus.fishermens_trap.data.client.FTSpriteSourceProvider;
import dev.uraneptus.fishermens_trap.data.server.FTRecipeProvider;
import dev.uraneptus.fishermens_trap.data.server.loot.FTLootTablesProvider;
import dev.uraneptus.fishermens_trap.data.server.tags.FTBiomeTagsProvider;
import dev.uraneptus.fishermens_trap.data.server.tags.FTBlockTagsProvider;
import dev.uraneptus.fishermens_trap.data.server.tags.FTItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(FishermensTrap.MOD_ID)
public class FishermensTrapNeo {

    public FishermensTrapNeo(IEventBus bus, ModContainer modContainer) {
        FishermensTrap.init();
        FTConfigNeo.init(modContainer);

        FTNeoforgeImpl.BLOCKS.register(bus);
        FTNeoforgeImpl.ITEMS.register(bus);
        FTNeoforgeImpl.BLOCK_ENTITIES.register(bus);
        FTNeoforgeImpl.MENU.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::gatherData);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        FTRegistries.initTabEntries();
    }

    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(includeClient, new FTLangProvider(packOutput));
        generator.addProvider(includeClient, new FTBlockStateProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FTItemModelProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FTSpriteSourceProvider(packOutput, lookupProvider, fileHelper));

        FTBlockTagsProvider blockTagProvider = new FTBlockTagsProvider(packOutput, lookupProvider, fileHelper);
        generator.addProvider(includeServer, blockTagProvider);
        generator.addProvider(includeServer, new FTItemTagsProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), fileHelper));
        generator.addProvider(includeServer, new FTBiomeTagsProvider(packOutput, lookupProvider, fileHelper));
        generator.addProvider(includeServer, new FTLootTablesProvider(packOutput, lookupProvider));
        generator.addProvider(includeServer, new FTRecipeProvider(packOutput, lookupProvider));
    }
}

package com.uraneptus.fishermens_trap;

import com.mojang.logging.LogUtils;
import com.uraneptus.fishermens_trap.core.data.client.FTBlockStateProvider;
import com.uraneptus.fishermens_trap.core.data.client.FTItemModelProvider;
import com.uraneptus.fishermens_trap.core.data.client.FTLangProvider;
import com.uraneptus.fishermens_trap.core.data.server.FTLootTableProvider;
import com.uraneptus.fishermens_trap.core.data.server.tags.FTBiomeTagsProvider;
import com.uraneptus.fishermens_trap.core.data.server.tags.FTBlockTagsProvider;
import com.uraneptus.fishermens_trap.core.data.server.tags.FTItemTagsProvider;
import com.uraneptus.fishermens_trap.core.registry.FTBlocks;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FishermensTrap.MOD_ID)
@Mod.EventBusSubscriber(modid = FishermensTrap.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FishermensTrap {
    public static final String MOD_ID = "fishermens_trap";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modPrefix(String path) {
        return new ResourceLocation(FishermensTrap.MOD_ID, path);
    }

    public FishermensTrap() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::gatherData);

        FTBlocks.BLOCKS.register(bus);
        FTItems.ITEMS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(includeClient, new FTBlockStateProvider(generator, fileHelper));
        generator.addProvider(includeClient, new FTItemModelProvider(generator, fileHelper));
        generator.addProvider(includeClient, new FTLangProvider(generator));

        FTBlockTagsProvider blockTagProvider = new FTBlockTagsProvider(generator, fileHelper);
        generator.addProvider(includeServer, blockTagProvider);
        generator.addProvider(includeServer, new FTItemTagsProvider(generator, blockTagProvider, fileHelper));
        generator.addProvider(includeServer, new FTBiomeTagsProvider(generator, fileHelper));
        generator.addProvider(includeServer, new FTLootTableProvider(generator));

/*
        generator.addProvider(includeServer, new SMAdvancementProvider(generator, fileHelper));
        generator.addProvider(includeServer, new SMRecipeProvider(generator));
 */
    }
}

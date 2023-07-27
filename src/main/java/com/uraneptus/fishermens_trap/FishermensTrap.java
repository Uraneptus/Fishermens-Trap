package com.uraneptus.fishermens_trap;

import com.mojang.logging.LogUtils;
import com.uraneptus.fishermens_trap.client.screen.FishtrapScreen;
import com.uraneptus.fishermens_trap.core.data.client.FTBlockStateProvider;
import com.uraneptus.fishermens_trap.core.data.client.FTItemModelProvider;
import com.uraneptus.fishermens_trap.core.data.client.FTLangProvider;
import com.uraneptus.fishermens_trap.core.data.client.FTSpriteSourceProvider;
import com.uraneptus.fishermens_trap.core.data.server.FTRecipeProvider;
import com.uraneptus.fishermens_trap.core.data.server.loot.FTLootTablesProvider;
import com.uraneptus.fishermens_trap.core.data.server.tags.FTItemTagsProvider;
import com.uraneptus.fishermens_trap.core.registry.FTBlockEntityType;
import com.uraneptus.fishermens_trap.core.registry.FTBlocks;
import com.uraneptus.fishermens_trap.core.registry.FTItems;
import com.uraneptus.fishermens_trap.core.registry.FTMenuType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

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
        bus.addListener(this::clientSetup);
        bus.addListener(this::gatherData);

        FTBlocks.BLOCKS.register(bus);
        FTBlockEntityType.BLOCK_ENTITY_TYPE.register(bus);
        FTItems.ITEMS.register(bus);
        FTMenuType.MENU.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(FTMenuType.FISHTRAP_MENU.get(), FishtrapScreen::new);
        });
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        CompletableFuture<TagsProvider.TagLookup<Block>> blockTagLookup = new ForgeBlockTagsProvider(packOutput, lookupProvider, fileHelper).contentsGetter();

        generator.addProvider(includeClient, new FTBlockStateProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FTItemModelProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FTLangProvider(packOutput));
        generator.addProvider(includeClient, new FTSpriteSourceProvider(packOutput, fileHelper));

        generator.addProvider(includeServer, new FTItemTagsProvider(packOutput, lookupProvider, blockTagLookup, fileHelper));
        generator.addProvider(includeServer, new FTLootTablesProvider(packOutput));
        generator.addProvider(includeServer, new FTRecipeProvider(packOutput));
    }
}

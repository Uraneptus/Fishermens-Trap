package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.client.screen.FishtrapScreen;
import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(value = FishermensTrap.MOD_ID, dist = Dist.CLIENT)
public class FishermensTrapNeoClient {

    public FishermensTrapNeoClient(IEventBus bus, ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        //TODO check if FTRegistries.TAB_ENTRIES exist here
        bus.addListener(this::buildTabContents);
        bus.addListener(this::registerScreens);
    }

    private void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        FTRegistries.TAB_ENTRIES.forEach((tab, itemList) -> {
            if (tabKey == tab) {
                for (ItemLike item : itemList) {
                    event.accept(item);
                }
            }
        });
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(FTRegistries.FISHTRAP_MENU.get(), FishtrapScreen::new);
    }
}

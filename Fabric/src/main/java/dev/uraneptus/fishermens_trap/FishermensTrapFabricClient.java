package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.client.screen.FishtrapScreen;
import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class FishermensTrapFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(FTRegistries.FISHTRAP_MENU.get(), FishtrapScreen::new);
    }
}

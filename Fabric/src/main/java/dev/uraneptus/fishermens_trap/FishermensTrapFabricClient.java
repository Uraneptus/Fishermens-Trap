package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.client.screen.FishtrapScreen;
import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class FishermensTrapFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(FTRegistries.FISHTRAP_MENU.get(), FishtrapScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(FTRegistries.FISHTRAP_BLOCK.get(), RenderType.cutout());
    }
}

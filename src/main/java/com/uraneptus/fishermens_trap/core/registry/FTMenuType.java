package com.uraneptus.fishermens_trap.core.registry;

import com.uraneptus.fishermens_trap.FishermensTrap;
import com.uraneptus.fishermens_trap.common.blocks.container.FishtrapMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTMenuType {

    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FishermensTrap.MOD_ID);

    public static final RegistryObject<MenuType<FishtrapMenu>> FISHTRAP_MENU = MENU.register("fishtrap_menu", () -> IForgeMenuType.create(FishtrapMenu::new));

}

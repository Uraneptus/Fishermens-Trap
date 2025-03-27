package dev.uraneptus.fishermens_trap;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public interface FabricMenuTypeExtension<T> {
    static <T extends AbstractContainerMenu> MenuType<T> create(Factory<T> factory) {
        return new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS);
    }

    T create(int id, Inventory inventory, RegistryFriendlyByteBuf buffer);

    interface Factory<T extends AbstractContainerMenu> extends MenuType.MenuSupplier<T> {
        T create(int id, Inventory inventory, FriendlyByteBuf buffer);

        @NotNull
        default T create(int id, Inventory inventory) {
            return this.create(id, inventory, null);
        }
    }
}

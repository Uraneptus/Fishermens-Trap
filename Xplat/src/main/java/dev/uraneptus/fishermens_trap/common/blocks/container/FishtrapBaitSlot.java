package dev.uraneptus.fishermens_trap.common.blocks.container;

import com.mojang.datafixers.util.Pair;
import dev.uraneptus.fishermens_trap.FishermensTrap;
import dev.uraneptus.fishermens_trap.common.tags.FTItemTags;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FishtrapBaitSlot extends Slot {
    public static final ResourceLocation BAIT_ICON = FishermensTrap.modPrefix("item/empty_slot_bait");

    public FishtrapBaitSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return pStack.is(FTItemTags.FISH_BAIT);
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(TextureAtlas.LOCATION_BLOCKS, BAIT_ICON);
    }
}

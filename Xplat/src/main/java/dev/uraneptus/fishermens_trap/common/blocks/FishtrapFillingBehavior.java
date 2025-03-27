package dev.uraneptus.fishermens_trap.common.blocks;

import dev.uraneptus.fishermens_trap.common.tags.FTItemTags;
import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FishtrapFillingBehavior {

    public static void handleItemInsertion(Container container, List<ItemStack> list, ItemStack baitItem, RandomSource random) {
        for (ItemStack itemStack : list) {
            if (!itemStack.isEmpty()) {
                fillFishtrap(container, itemStack, baitItem, random, FTConfig.INSTANCE.fullStackCatch());
            }
        }
    }

    private static ItemStack fillFishtrap(Container container, ItemStack stack, ItemStack baitItem, RandomSource random, boolean stacked) {
        if (container == null || stack.isEmpty())
            return stack;

        //Collect all slots that have a water bucket
        List<Integer> bucketSlots = new ArrayList<>();
        for (int i = 1; i < container.getContainerSize(); i++) {
            if (container.getItem(i).is(Items.WATER_BUCKET)) {
                bucketSlots.add(i);
            }
        }

        // not stackable -> just insert into a new slot
        if (!stacked || !stack.isStackable()) {
            return insertNonStacked(container, stack, baitItem, random, bucketSlots);
        }

        int sizeInventory = container.getContainerSize();
        boolean catchBucketFish = random.nextFloat() < FTConfig.INSTANCE.fishBucketChance();

        if (bucketSlots.isEmpty() || !catchBucketFish) {
            // go through the inventory and try to fill up already existing items
            for (int i = 1; i < sizeInventory; i++) {
                ItemStack slot = container.getItem(i);
                if (ItemStack.isSameItemSameComponents(slot, stack)) {
                    stack = insertItem(container, i, stack);
                    baitItem.shrink(1);
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }

            // insert remainder into empty slots
            if (!stack.isEmpty()) {
                iterateAndAdd(container, stack, baitItem);
            }
        } else {
            stack = fishWithBucket(container, stack, baitItem, random, bucketSlots);
        }

        return stack;
    }

    private static ItemStack insertNonStacked(Container container, ItemStack stack, ItemStack baitItem, RandomSource random, List<Integer> bucketSlots) {
        if (container == null || stack.isEmpty())
            return stack;

        //Check if there are any empty slots left
        boolean emptySlotsLeft = false;
        for (int i = 1; i < container.getContainerSize(); i++) {
            if (container.getItem(i).isEmpty()) {
                emptySlotsLeft = true;
                break;
            }
        }

        //Exit if there are no empty slots and no water buckets that could be filled
        if (!emptySlotsLeft && bucketSlots.isEmpty()) {
            return stack;
        }

        boolean catchBucketFish = random.nextFloat() < FTConfig.INSTANCE.fishBucketChance() || !emptySlotsLeft;

        if (bucketSlots.isEmpty() || !catchBucketFish) {
            iterateAndAdd(container, stack, baitItem);
        } else {
            stack = fishWithBucket(container, stack, baitItem, random, bucketSlots);
        }


        return stack;
    }

    private static ItemStack fishWithBucket(Container container, ItemStack stack, ItemStack baitItem, RandomSource random, List<Integer> bucketSlots) {
        int selectedSlot = bucketSlots.get(random.nextInt(bucketSlots.size()));
        ItemStack bucket = container.getItem(selectedSlot);
        ResourceLocation regName =  BuiltInRegistries.ITEM.getKey(stack.getItem());
        ResourceLocation bucketFishLocation = ResourceLocation.fromNamespaceAndPath(Objects.requireNonNull(regName).getNamespace(), regName.getPath() + "_bucket");
        if (BuiltInRegistries.ITEM.containsKey(bucketFishLocation)) {
            bucket.shrink(1);
            stack = insertItem(container, selectedSlot, Objects.requireNonNull(BuiltInRegistries.ITEM.get(bucketFishLocation)).getDefaultInstance());
            baitItem.shrink(1);
        }
        return stack;
    }

    private static ItemStack iterateAndAdd(Container container, ItemStack stack, ItemStack baitItem) {
        for (int i = 1; i < container.getContainerSize(); i++) {
            if (container.getItem(i).isEmpty()) {
                baitItem.shrink(1);
                stack = insertItem(container, i, stack);
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }

                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack insertItem(Container container, int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (!isItemValid(slot, stack)) {
            return stack;
        }

        if (slot < 0 || slot >= container.getContainerSize()) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + container.getContainerSize() + ")");
        }

        /// Only if stackable
        ItemStack existing = container.getItem(slot);

        int limit = getCollectStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(stack, existing)) {
                return stack;
            }
            limit -= existing.getCount();
        }

        if (limit <= 0) {
            return stack;
        }

        boolean reachedLimit = stack.getCount() > limit;

        if (existing.isEmpty()) {
            container.setItem(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
        } else {
            existing.grow(reachedLimit ? limit : stack.getCount());
        }

        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }

    private static boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == 0) {
            return stack.is(FTItemTags.FISH_BAIT);
        }
        return true;
    }

    private static int getCollectStackLimit(int slot, ItemStack stack) {
        return slot != 0 && !FTConfig.INSTANCE.fullStackCatch() ? 1 : stack.getMaxStackSize();
    }


}

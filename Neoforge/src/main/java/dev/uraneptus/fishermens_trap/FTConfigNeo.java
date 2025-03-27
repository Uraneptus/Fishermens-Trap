package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FTConfigNeo implements FTConfig {
    public static final ModConfigSpec.ConfigValue<Integer> MIN_TICKS_TO_FISH;
    public static final ModConfigSpec.ConfigValue<Integer> MAX_TICKS_TO_FISH;
    public static final ModConfigSpec.DoubleValue FISH_BUCKET_CHANCE;
    public static final ModConfigSpec.ConfigValue<Boolean> FULL_STACK_CATCH;
    public static final ModConfigSpec COMMON;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        MIN_TICKS_TO_FISH = COMMON_BUILDER.comment("The minimum amount of ticks that have to pass until the next item can be fished using the Fish Trap. Value must be below max value. (default = 4800)").define("min_ticks", 4800);
        MAX_TICKS_TO_FISH = COMMON_BUILDER.comment("The maximum amount of ticks that may pass until the next item can be fished by the Fish Trap. Value must be above min value. (default = 8000)").define("max_ticks", 8000);
        FISH_BUCKET_CHANCE = COMMON_BUILDER.comment("The chance with which a living fish is caught in a bucket if a water bucket is placed inside the Fish Trap. (default = 0.15)").defineInRange("chance", 0.15, 0.0, 1.0);
        FULL_STACK_CATCH = COMMON_BUILDER.comment("Makes the fish trap able to catch up to a full stack of fish per slot (default = false)").define("enable_full_stack_catch", false);

        COMMON = COMMON_BUILDER.build();
    }

    @Override
    public int minTicksToFish() {
        return MIN_TICKS_TO_FISH.get();
    }

    @Override
    public int maxTicksToFish() {
        return MIN_TICKS_TO_FISH.get();
    }

    @Override
    public double fishBucketChance() {
        return FISH_BUCKET_CHANCE.get();
    }

    @Override
    public boolean fullStackCatch() {
        return FULL_STACK_CATCH.get();
    }
}

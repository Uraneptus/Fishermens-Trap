package com.uraneptus.fishermens_trap;

import net.minecraftforge.common.ForgeConfigSpec;

public class FTConfig {
    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_TICKS_TO_FISH;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_TICKS_TO_FISH;
    public static final ForgeConfigSpec COMMON;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.push("Fish Trap Fishing");
        MIN_TICKS_TO_FISH = COMMON_BUILDER.comment("The minimum amount of ticks that have to pass until the next item can be fished using the Fish Trap. Value must be below max value. (default = 4800)").define("Min ticks to fish", 4800);
        MAX_TICKS_TO_FISH = COMMON_BUILDER.comment("The maximum amount of ticks that may pass until the next item is able to be fished using the Fish Trap. Value must be above min value. (default = 8000)").define("Max ticks to fish", 8000);
        COMMON_BUILDER.pop();

        COMMON = COMMON_BUILDER.build();
    }
}

package dev.uraneptus.fishermens_trap;

import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FTConfigNeo {

    public static void init(ModContainer modContainer) {
        Pair<Config, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Config::new);
        FishermensTrap.CONFIG = specPair.getLeft();
        modContainer.registerConfig(ModConfig.Type.COMMON, specPair.getRight());
    }

    public static class Config implements FTConfig {
        private final ModConfigSpec.ConfigValue<Integer> MIN_TICKS_TO_FISH;
        private final ModConfigSpec.ConfigValue<Integer> MAX_TICKS_TO_FISH;
        private final ModConfigSpec.DoubleValue FISH_BUCKET_CHANCE;
        private final ModConfigSpec.ConfigValue<Boolean> FULL_STACK_CATCH;

        public Config(ModConfigSpec.Builder builder) {
            MIN_TICKS_TO_FISH = builder.comment("The minimum amount of ticks that have to pass until the next item can be fished using the Fish Trap. Value must be below max value. (default = 4800)").define("min_ticks", 4800);
            MAX_TICKS_TO_FISH = builder.comment("The maximum amount of ticks that may pass until the next item can be fished by the Fish Trap. Value must be above min value. (default = 8000)").define("max_ticks", 8000);
            FISH_BUCKET_CHANCE = builder.comment("The chance with which a living fish is caught in a bucket if a water bucket is placed inside the Fish Trap. (default = 0.15)").defineInRange("chance", 0.15, 0.0, 1.0);
            FULL_STACK_CATCH = builder.comment("Makes the fish trap able to catch up to a full stack of fish per slot (default = false)").define("enable_full_stack_catch", false);
        }

        @Override
        public int minTicksToFish() {
            return MIN_TICKS_TO_FISH.get();
        }

        @Override
        public int maxTicksToFish() {
            return MAX_TICKS_TO_FISH.get();
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
}

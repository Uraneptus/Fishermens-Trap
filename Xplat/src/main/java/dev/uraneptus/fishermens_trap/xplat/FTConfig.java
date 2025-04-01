package dev.uraneptus.fishermens_trap.xplat;

import dev.uraneptus.fishermens_trap.FishermensTrap;

public interface FTConfig {
    int minTicksToFish();
    int maxTicksToFish();
    double fishBucketChance();
    boolean fullStackCatch();
}

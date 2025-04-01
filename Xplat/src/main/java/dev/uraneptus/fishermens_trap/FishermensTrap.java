package dev.uraneptus.fishermens_trap;

import com.mojang.logging.LogUtils;
import dev.uraneptus.fishermens_trap.common.registry.FTRegistries;
import dev.uraneptus.fishermens_trap.xplat.FTAbstractions;
import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.ServiceLoader;

public class FishermensTrap {
    public static final String MOD_ID = "fishermens_trap";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static FTAbstractions ABSTRACTIONS = FishermensTrap.loadService(FTAbstractions.class);
    public static FTConfig CONFIG;

    public static ResourceLocation modPrefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(FishermensTrap.MOD_ID, path);
    }

    public static void init() {
        FTRegistries.init();
    }

    public static <T> T loadService(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}

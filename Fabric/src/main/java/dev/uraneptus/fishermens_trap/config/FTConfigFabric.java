package dev.uraneptus.fishermens_trap.config;

import dev.uraneptus.fishermens_trap.FishermensTrap;
import dev.uraneptus.fishermens_trap.xplat.FTConfig;
import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.*;

import static io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes.*;

public class FTConfigFabric {
    private static final Logger LOGGER = LogManager.getLogger(FTConfigFabric.class);

    private static void writeDefaultConfig(ConfigTree config, Path path, JanksonValueSerializer serializer) {
        try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
            FiberSerialization.serialize(config, s, serializer);
        } catch (FileAlreadyExistsException ignored) {} catch (IOException e) {
            LOGGER.error("Error writing default config", e);
        }
    }

    private static void setupConfig(ConfigTree config, Path p, JanksonValueSerializer serializer) {
        writeDefaultConfig(config, p, serializer);

        try (InputStream s = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
            FiberSerialization.deserialize(config, s, serializer);
        } catch (IOException | ValueDeserializationException e) {
            LOGGER.error("Error loading config from {}", p, e);
        }
    }

    public static void setup() {
        try {
            Files.createDirectory(Paths.get("config"));
        } catch (FileAlreadyExistsException ignored) {} catch (IOException e) {
            LOGGER.warn("Failed to make config dir", e);
        }

        JanksonValueSerializer serializer = new JanksonValueSerializer(false);
        ConfigTree common = COMMON.configure(ConfigTree.builder());
        setupConfig(common, Paths.get("config", FishermensTrap.MOD_ID + "-common.json5"), serializer);
        FishermensTrap.CONFIG = COMMON;
    }

    public static class Common implements FTConfig {
        private final PropertyMirror<Integer> minTicksToFish = PropertyMirror.create(INTEGER);
        private final PropertyMirror<Integer> maxTicksToFish = PropertyMirror.create(INTEGER);
        private final PropertyMirror<Double> fishBucketChance = PropertyMirror.create(DOUBLE);
        private final PropertyMirror<Boolean> fullStackCatch = PropertyMirror.create(BOOLEAN);

        public ConfigTree configure(ConfigTreeBuilder builder) {
            return builder.beginValue("minTicksToFish", INTEGER, 4800)
                    .withComment("The minimum amount of ticks that have to pass until the next item can be fished using the Fish Trap. Value must be below max value. (default = 4800)")
                    .finishValue(minTicksToFish::mirror)

                    .beginValue("maxTicksToFish", INTEGER, 8000)
                    .withComment("The maximum amount of ticks that may pass until the next item can be fished by the Fish Trap. Value must be above min value. (default = 8000)")
                    .finishValue(maxTicksToFish::mirror)

                    .beginValue("fishBucketChance", DOUBLE, 0.15)
                    .withComment("The chance with which a living fish is caught in a bucket if a water bucket is placed inside the Fish Trap. (default = 0.15)")
                    .finishValue(fishBucketChance::mirror)

                    .beginValue("fullStackCatch", BOOLEAN, false)
                    .withComment("Makes the fish trap able to catch up to a full stack of fish per slot (default = false)")
                    .finishValue(fullStackCatch::mirror)
                    .build();
        }

        @Override
        public int minTicksToFish() {
            return minTicksToFish.getValue();
        }

        @Override
        public int maxTicksToFish() {
            return maxTicksToFish.getValue();
        }

        @Override
        public double fishBucketChance() {
            return fishBucketChance.getValue();
        }

        @Override
        public boolean fullStackCatch() {
            return fullStackCatch.getValue();
        }
    }

    private static final Common COMMON = new Common();
}

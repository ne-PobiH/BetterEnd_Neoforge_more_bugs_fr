package org.aiblib.wover.generator.impl.end;

import org.aiblib.wover.entrypoint.LibWoverWorldGenerator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public final class EndWorldgenProfiler {
    private static final boolean ENABLED = Boolean.parseBoolean(System.getProperty("betterend.worldgenProfile", "true"));
    private static final long LOG_INTERVAL_NANOS = Long.getLong("betterend.worldgenProfileIntervalMs", 10000L) * 1_000_000L;
    private static final AtomicLong NEXT_LOG = new AtomicLong(System.nanoTime() + LOG_INTERVAL_NANOS);

    private static final Section BIOME_SOURCE = new Section("biomeSource");
    private static final Section IS_LAND = new Section("isLand");
    private static final Section IS_LAND_MISS = new Section("isLandMiss");
    private static final Section TERRAIN_DENSITY = new Section("terrainDensity");
    private static final Section AVG_DEPTH = new Section("avgDepth");
    private static final Section BIOME_DATA = new Section("biomeData");

    private static final LongAdder isLandHits = new LongAdder();
    private static final LongAdder isLandMisses = new LongAdder();
    private static final LongAdder biomeDataHits = new LongAdder();
    private static final LongAdder biomeDataMisses = new LongAdder();
    private static final LongAdder biomeSourceHits = new LongAdder();
    private static final LongAdder biomeSourceMisses = new LongAdder();

    private EndWorldgenProfiler() {
    }

    public static long start() {
        return ENABLED ? System.nanoTime() : 0L;
    }

    public static void biomeSource(long start) {
        record(BIOME_SOURCE, start);
    }

    public static void biomeSourceCache(boolean hit) {
        if (!ENABLED) return;
        if (hit) {
            biomeSourceHits.increment();
        } else {
            biomeSourceMisses.increment();
        }
    }

    public static void terrainDensity(long start) {
        record(TERRAIN_DENSITY, start);
    }

    public static void averageDepth(long start) {
        record(AVG_DEPTH, start);
    }

    public static void biomeData(long start, boolean hit) {
        if (!ENABLED) return;
        if (hit) {
            biomeDataHits.increment();
        } else {
            biomeDataMisses.increment();
        }
        record(BIOME_DATA, start);
    }

    public static void isLand(long start, boolean cacheHit) {
        if (!ENABLED) return;
        if (cacheHit) {
            isLandHits.increment();
            record(IS_LAND, start);
        } else {
            isLandMisses.increment();
            long elapsed = System.nanoTime() - start;
            IS_LAND.add(elapsed);
            IS_LAND_MISS.add(elapsed);
            maybeLog();
        }
    }

    private static void record(Section section, long start) {
        if (!ENABLED) return;
        section.add(System.nanoTime() - start);
        maybeLog();
    }

    private static void maybeLog() {
        long now = System.nanoTime();
        long next = NEXT_LOG.get();
        if (now < next || !NEXT_LOG.compareAndSet(next, now + LOG_INTERVAL_NANOS)) {
            return;
        }

        long isLandHitCount = isLandHits.sum();
        long isLandMissCount = isLandMisses.sum();
        long biomeDataHitCount = biomeDataHits.sum();
        long biomeDataMissCount = biomeDataMisses.sum();
        long biomeSourceHitCount = biomeSourceHits.sum();
        long biomeSourceMissCount = biomeSourceMisses.sum();
        LibWoverWorldGenerator.C.log.info(
                "[BetterEnd Worldgen Profile] {} | {} | {} | {} | {} | {} | biomeSourceCache={}/{} ({}%) | isLandCache={}/{} ({}%) | biomeDataCache={}/{} ({}%)",
                BIOME_SOURCE.snapshot(),
                IS_LAND.snapshot(),
                IS_LAND_MISS.snapshot(),
                TERRAIN_DENSITY.snapshot(),
                AVG_DEPTH.snapshot(),
                BIOME_DATA.snapshot(),
                biomeSourceHitCount,
                biomeSourceHitCount + biomeSourceMissCount,
                percent(biomeSourceHitCount, biomeSourceHitCount + biomeSourceMissCount),
                isLandHitCount,
                isLandHitCount + isLandMissCount,
                percent(isLandHitCount, isLandHitCount + isLandMissCount),
                biomeDataHitCount,
                biomeDataHitCount + biomeDataMissCount,
                percent(biomeDataHitCount, biomeDataHitCount + biomeDataMissCount)
        );
    }

    private static long percent(long value, long total) {
        return total == 0 ? 0 : Math.round(value * 100.0 / total);
    }

    private static final class Section {
        private final String name;
        private final LongAdder count = new LongAdder();
        private final LongAdder nanos = new LongAdder();

        private Section(String name) {
            this.name = name;
        }

        private void add(long elapsedNanos) {
            count.increment();
            nanos.add(elapsedNanos);
        }

        private String snapshot() {
            long c = count.sum();
            long n = nanos.sum();
            long avgMicros = c == 0 ? 0 : n / c / 1000L;
            return name + "=" + c + " calls/" + (n / 1_000_000L) + "ms avg=" + avgMicros + "us";
        }
    }
}

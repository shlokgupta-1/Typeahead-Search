package com.typeahead.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MetricsService {

    private final AtomicLong cacheHits =
            new AtomicLong();

    private final AtomicLong cacheMisses =
            new AtomicLong();

    public void incrementHit() {
        cacheHits.incrementAndGet();
        }

    public void incrementMiss() {
        cacheMisses.incrementAndGet();
        }

    

    public Map<String,Object> getMetrics() {

        long hits =
                cacheHits.get();

        long misses =
                cacheMisses.get();

        long total =
                hits + misses;

        double hitRate =
                total == 0
                ? 0
                : ((double) hits / total) * 100;

        return Map.of(
                "cacheHits", hits,
                "cacheMisses", misses,
                "hitRate", hitRate
        );
    }
}
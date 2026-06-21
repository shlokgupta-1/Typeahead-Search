package com.typeahead.batch;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Component
public class SearchBuffer {

    private final ConcurrentHashMap<
            String,
            LongAdder> buffer =
            new ConcurrentHashMap<>();

    public void increment(
            String query
    ) {

        buffer.computeIfAbsent(
                query.toLowerCase(),
                q -> new LongAdder()
        ).increment();
    }

    public Map<String, LongAdder> getBuffer() {
        return buffer;
    }

    public void clear() {
        buffer.clear();
    }
}
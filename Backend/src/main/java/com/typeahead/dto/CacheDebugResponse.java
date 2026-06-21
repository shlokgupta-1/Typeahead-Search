package com.typeahead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class CacheDebugResponse {

    private String prefix;

    private String node;

    private boolean cacheHit;

    private long timestamp;
}
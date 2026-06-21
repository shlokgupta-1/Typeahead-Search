package com.typeahead.controller;

import com.typeahead.cache.ConsistentHashRing;
import com.typeahead.dto.CacheDebugResponse;
import com.typeahead.service.CacheService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheDebugController {

    private final ConsistentHashRing ring;

    private final CacheService cacheService;

    @GetMapping("/debug")
    public CacheDebugResponse debug(
            @RequestParam String prefix
    ) {

        return new CacheDebugResponse(
                prefix,
                ring.getNode(prefix),
                cacheService.exists(prefix),
                System.currentTimeMillis()
        );
    }
}
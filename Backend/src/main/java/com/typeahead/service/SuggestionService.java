package com.typeahead.service;

import com.typeahead.trie.QueryMetadata;
import com.typeahead.trie.Trie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final Trie trie;
    private final MetricsService metricsService;
    private final CacheService cacheService;

    
    public List<QueryMetadata> suggest(
            String prefix
    ) {

        if(prefix == null || prefix.length() < 3) {
            return List.of();
        }
        
        if(cacheService.exists(prefix)) {

            metricsService.incrementHit();

            System.out.println(
                "CACHE HIT -> " + prefix
            );

            return cacheService
                    .getSuggestions(prefix);
        }

        metricsService.incrementMiss();

        System.out.println(
            "CACHE MISS -> " + prefix
        );

        List<QueryMetadata> suggestions =
                trie.getSuggestions(prefix);

        cacheService.putSuggestions(
                prefix,
                suggestions
        );

        return suggestions;
    }
}
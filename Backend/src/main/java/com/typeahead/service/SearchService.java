package com.typeahead.service;

import com.typeahead.batch.SearchBuffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchBuffer buffer;

    private final CacheService cacheService;

    public void search(
            String query
    ) {

        if(query == null) {
            return;
        }

        query = query.trim().toLowerCase();

        /*
         * Ignore very short searches
         * Prevents:
         * ip
         * iph
         * ja
         * sp
         */
        if(query.length() < 5) {
            return;
        }

        /*
         * Ignore searches ending with space
         * Example:
         * "spring "
         * "iphone "
         */
        if(query.endsWith(" ")) {
            return;
        }

        buffer.increment(query);

        int prefixLength =
                Math.min(
                        3,
                        query.length()
                );

        String prefix =
                query.substring(
                        0,
                        prefixLength
                );

        cacheService.evict(prefix);
    }
} 
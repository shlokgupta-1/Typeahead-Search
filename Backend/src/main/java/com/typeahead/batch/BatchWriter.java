package com.typeahead.batch;

import com.typeahead.entity.SearchQuery;
import com.typeahead.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

@Component
@RequiredArgsConstructor
public class BatchWriter {
    

    private final SearchBuffer buffer;

    private final SearchQueryRepository
            repository;

    @Scheduled(fixedRate = 5000)
    public void flush() {

        Map<String, LongAdder> data =
                buffer.getBuffer();

            System.out.println(
            "Batch size = "
            + buffer.getBuffer().size()
        );

        data.forEach((query,count) -> {

            System.out.println(
                query + " => " + count.longValue()
            );
        });

        if(data.isEmpty()) {
            return;
        }

        System.out.println(
                "Flushing batch..."
        );

        data.forEach((query,count) -> {

            SearchQuery entity =
                    repository
                    .findByQueryTextIgnoreCase(
                            query
                    )
                    .orElse(
                            SearchQuery.builder()
                                    .queryText(query)
                                    .searchCount(0L)
                                    .trendingScore(0.0)
                                    .createdAt(
                                            LocalDateTime.now()
                                    )
                                    .build()
                    );

            entity.setSearchCount(
                    entity.getSearchCount()
                    + count.longValue()
            );

            entity.setLastSearchedAt(
                    LocalDateTime.now()
            );

            repository.save(entity);
        });

        buffer.clear();
    }

    
}
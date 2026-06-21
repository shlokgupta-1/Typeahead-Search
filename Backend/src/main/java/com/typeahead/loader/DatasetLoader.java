package com.typeahead.loader;

import com.typeahead.entity.SearchQuery;
import com.typeahead.repository.SearchQueryRepository;
import com.typeahead.trie.Trie;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatasetLoader {

    private final SearchQueryRepository repository;
    private final Trie trie;

    @PostConstruct
    public void loadDataset() {

        try {

            long dbCount = repository.count();

            /*
             * DATABASE ALREADY POPULATED
             * Rebuild Trie from DB
             */
            if(dbCount > 0) {

                System.out.println(
                        "Loading Trie from database..."
                );

                repository.findAll()
                        .forEach(query -> {

                            trie.insert(
                                    query.getQueryText(),
                                    query.getSearchCount(),
                                    query.getTrendingScore()
                            );
                        });

                System.out.println(
                        "Trie rebuilt from "
                        + dbCount
                        + " records"
                );

                return;
            }

            /*
             * FIRST STARTUP
             * Load CSV -> DB + Trie
             */
            long start =
                    System.currentTimeMillis();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new ClassPathResource(
                                            "dataset/search_queries.csv"
                                    ).getInputStream()
                            )
                    );

            reader.readLine();

            String line;

            int count = 0;

            List<SearchQuery> batch =
                    new ArrayList<>();

            while((line = reader.readLine()) != null) {

                String[] parts =
                        line.split(",");

                if(parts.length < 2) {
                    continue;
                }

                String query =
                        parts[0].trim();

                long searchCount =
                        Long.parseLong(
                                parts[1].trim()
                        );

                SearchQuery entity =
                        SearchQuery.builder()
                                .queryText(query)
                                .searchCount(searchCount)
                                .trendingScore(0.0)
                                .createdAt(LocalDateTime.now())
                                .lastSearchedAt(LocalDateTime.now())
                                .build();

                batch.add(entity);

                trie.insert(
                        query,
                        searchCount,
                        0.0
                );

                count++;

                if(count % 1000 == 0) {

                    repository.saveAll(batch);

                    batch.clear();

                    System.out.println(
                            "Loaded: " + count
                    );
                }
            }

            if(!batch.isEmpty()) {

                repository.saveAll(batch);
            }

            System.out.println(
                    "Loaded "
                    + count
                    + " queries in "
                    + (
                        System.currentTimeMillis()
                        - start
                    )
                    + " ms"
            );

        }
        catch(Exception ex) {

            ex.printStackTrace();
        }
    }
}
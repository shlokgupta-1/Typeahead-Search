package com.typeahead.service;

import com.typeahead.entity.SearchQuery;
import com.typeahead.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendingService {

    private final SearchQueryRepository repository;

    @Scheduled(fixedRate = 60000)
    public void recomputeTrendingScores() {

        List<SearchQuery> queries =
                repository.findAll();

        LocalDateTime now =
                LocalDateTime.now();

        for(SearchQuery query : queries) {

            if(query.getLastSearchedAt() == null) {
                query.setLastSearchedAt(
                        LocalDateTime.now()
                );
            }

            long ageMinutes =
                    Math.max(
                            1,
                            Duration.between(
                                    query.getLastSearchedAt(),
                                    now
                            ).toMinutes()
                    );

            double popularity =
                    Math.log(
                            query.getSearchCount() + 1
                    );

            double recencyBoost =
                    100.0 / ageMinutes;

            double score =
                    0.7 * popularity
                    + 0.3 * recencyBoost;

            query.setTrendingScore(score);
        }

        repository.saveAll(queries);

        System.out.println(
                "Trending scores updated"
        );
    }

    public List<SearchQuery> getTrending() {

        return repository
                .findTop10ByOrderByTrendingScoreDesc();
    }
}
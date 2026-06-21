package com.typeahead.repository;

import com.typeahead.entity.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface SearchQueryRepository
        extends JpaRepository<SearchQuery, Long> {

        List<SearchQuery> findTop10ByOrderByTrendingScoreDesc();


    Optional<SearchQuery> findByQueryTextIgnoreCase(
            String queryText
    );
}

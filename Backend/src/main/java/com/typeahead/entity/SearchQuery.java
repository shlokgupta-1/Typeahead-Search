package com.typeahead.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_query")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "query_text", unique = true, nullable = false)
    private String queryText;

    @Column(name = "search_count")
    private Long searchCount;

    @Column(name = "trending_score")
    private Double trendingScore;

    @Column(name = "last_searched_at")
    private LocalDateTime lastSearchedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
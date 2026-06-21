package com.typeahead.trie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryMetadata
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private String query;

    private long searchCount;

    private double trendingScore;
}
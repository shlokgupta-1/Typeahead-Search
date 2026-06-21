package com.typeahead.trie;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class Trie {

    private final TrieNode root =
            new TrieNode();

    public void insert(
            String query,
            long searchCount,
            double trendingScore
    ) {

        TrieNode current = root;

        QueryMetadata metadata =
                new QueryMetadata(
                        query,
                        searchCount,
                        trendingScore
                );

        for(char ch :
                query.toLowerCase().toCharArray()) {

            current =
                    current.getChildren()
                            .computeIfAbsent(
                                    ch,
                                    c -> new TrieNode()
                            );

            updateTopSuggestions(
                    current,
                    metadata
            );
        }

        current.setEndOfWord(true);
    }

    private void updateTopSuggestions(
            TrieNode node,
            QueryMetadata metadata
    ) {

        node.getTopSuggestions()
                .removeIf(
                        existing ->
                                existing.getQuery()
                                        .equalsIgnoreCase(
                                                metadata.getQuery()
                                        )
                );

        node.getTopSuggestions()
                .add(metadata);

        node.getTopSuggestions()
                .sort(
                        Comparator
                                .comparingLong(
                                        QueryMetadata::getSearchCount
                                )
                                .reversed()
                );

        if(node.getTopSuggestions().size() > 10) {

            node.getTopSuggestions()
                    .remove(
                            node.getTopSuggestions().size() - 1
                    );
        }
    }

    public List<QueryMetadata> getSuggestions(
            String prefix
    ) {

        TrieNode current = root;

        for(char ch :
                prefix.toLowerCase().toCharArray()) {

            current =
                    current.getChildren().get(ch);

            if(current == null) {

                return new ArrayList<>();
            }
        }

        return current.getTopSuggestions();
    }
}
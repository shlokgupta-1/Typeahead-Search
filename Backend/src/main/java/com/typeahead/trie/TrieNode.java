package com.typeahead.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {

    private final Map<Character, TrieNode> children =
            new HashMap<>();

    private boolean endOfWord;

    private final List<QueryMetadata> topSuggestions =
            new ArrayList<>();

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public List<QueryMetadata> getTopSuggestions() {
        return topSuggestions;
    }
}
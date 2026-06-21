package com.typeahead.controller;

import com.typeahead.trie.QueryMetadata;
import com.typeahead.trie.Trie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final Trie trie;

    @GetMapping
    public List<QueryMetadata> test() {

        trie.insert(
                "iphone",
                100000,
                95.0
        );

        trie.insert(
                "iphone 15",
                85000,
                90.0
        );

        trie.insert(
                "iphone charger",
                60000,
                80.0
        );

        return trie.getSuggestions(
                "iph"
        );
    }
}
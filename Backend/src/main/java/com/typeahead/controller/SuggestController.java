package com.typeahead.controller;

import com.typeahead.service.SuggestionService;
import com.typeahead.trie.QueryMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/suggest")
@RequiredArgsConstructor
public class SuggestController {

    private final SuggestionService
            suggestionService;

    @GetMapping
    public List<QueryMetadata> suggest(
            @RequestParam String q
    ) {

        return suggestionService
                .suggest(q);
    }
}
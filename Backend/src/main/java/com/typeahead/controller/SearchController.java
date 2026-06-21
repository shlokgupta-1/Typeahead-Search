package com.typeahead.controller;

import com.typeahead.dto.SearchRequest;
import com.typeahead.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService
            searchService;

    @PostMapping
    public Map<String,String> search(
            @RequestBody SearchRequest request
    ) {

        searchService.search(
                request.getQuery()
        );

        return Map.of(
                "message",
                "Searched"
        );
    }
}
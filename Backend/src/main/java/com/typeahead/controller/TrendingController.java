package com.typeahead.controller;

import com.typeahead.entity.SearchQuery;
import com.typeahead.service.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/trending")
@RequiredArgsConstructor
public class TrendingController {

    private final TrendingService
            trendingService;

    @GetMapping
    public List<SearchQuery> trending() {

        return trendingService
                .getTrending();
    }
}
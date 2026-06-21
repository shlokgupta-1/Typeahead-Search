package com.typeahead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuggestResponse {

    private String query;
    private Long count;
}
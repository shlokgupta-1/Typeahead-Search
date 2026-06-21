package com.typeahead.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    private String query;

    private boolean completeSearch;
}
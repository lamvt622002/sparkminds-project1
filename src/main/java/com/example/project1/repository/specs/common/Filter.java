package com.example.project1.repository.specs.common;

import com.example.project1.enums.QueryOperator;

import java.util.List;

public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<String> values;
}

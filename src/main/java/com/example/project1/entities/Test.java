package com.example.project1.entities;

import lombok.*;

import java.util.List;

@Data
public class Test {
    private String name;

    @NonNull
    private int count;

    private final String hehe;

    @Singular("list")
    private List<String> list;

}

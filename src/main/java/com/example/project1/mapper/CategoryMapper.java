package com.example.project1.mapper;

import com.example.project1.entities.Category;
import com.example.project1.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryMapper {
    List<CategoryResponse> listCategoryToListCategoryResponse(List<Category> categories);
}

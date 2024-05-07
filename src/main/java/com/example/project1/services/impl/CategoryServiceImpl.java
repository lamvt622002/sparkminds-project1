package com.example.project1.services.impl;

import com.example.project1.mapper.CategoryMapper;
import com.example.project1.payload.response.CategoryResponse;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryMapper.listCategoryToListCategoryResponse(categoryRepository.findAll());
    }
}

package com.example.project1.mapper.impl;

import com.example.project1.entities.Author;
import com.example.project1.entities.Book;
import com.example.project1.entities.Category;
import com.example.project1.mapper.CategoryMapper;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.payload.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {
    private final ModelMapper mapper;
    @Override
    public List<CategoryResponse> listCategoryToListCategoryResponse(List<Category> categories) {
        Converter<Category, CategoryResponse> categoryResponseConverter = context -> {
            Category src = context.getSource();
            return CategoryResponse.builder()
                    .id(src.getId())
                    .categoryName(src.getCategoryName())
                    .build();
        };
        TypeMap<Category, CategoryResponse> typeMap = mapper.getTypeMap(Category.class, CategoryResponse.class);
        if (typeMap == null) {
            mapper.createTypeMap(Category.class, CategoryResponse.class).setConverter(categoryResponseConverter);
        }
        return categories.stream().map(category -> mapper.map(category, CategoryResponse.class)).collect(Collectors.toList());
    }
}

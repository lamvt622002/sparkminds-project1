package com.example.project1.mapper.impl;

import com.example.project1.entities.Author;
import com.example.project1.mapper.AuthorMapper;
import com.example.project1.payload.response.AuthorResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorMapperImpl implements AuthorMapper {
    private final ModelMapper mapper;
    @Override
    public List<AuthorResponse> listAuthorToListAuthorResponse(List<Author> authors) {
        Converter<Author, AuthorResponse> converter = context ->{
            Author src = context.getSource();
            return AuthorResponse.builder()
                    .id(src.getId())
                    .fullName(src.getFirstName() + " "  + src.getLastName())
                    .birthDay(src.getBirthDay())
                    .build();
        };

        TypeMap<Author, AuthorResponse> typeMap = mapper.getTypeMap(Author.class, AuthorResponse.class);
        if (typeMap == null) {
            mapper.createTypeMap(Author.class, AuthorResponse.class).setConverter(converter);
        }
        return authors.stream().map(author -> mapper.map(author, AuthorResponse.class)).collect(Collectors.toList());
    }
}

package com.example.project1.mapper.impl;

import com.example.project1.entities.Publisher;
import com.example.project1.mapper.PublisherMapper;
import com.example.project1.payload.response.PublisherResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherMapperImpl implements PublisherMapper {
    private final ModelMapper mapper;
    @Override
    public List<PublisherResponse> listPublisherToListPublisherResponse(List<Publisher> publishers) {
        Converter<Publisher, PublisherResponse> categoryResponseConverter = context -> {
            Publisher src = context.getSource();
            return PublisherResponse.builder()
                    .id(src.getId())
                    .publisherName(src.getPublisherName())
                    .build();
        };
        TypeMap<Publisher, PublisherResponse> typeMap = mapper.getTypeMap(Publisher.class, PublisherResponse.class);
        if (typeMap == null) {
            mapper.createTypeMap(Publisher.class, PublisherResponse.class).setConverter(categoryResponseConverter);
        }
        return publishers.stream().map(publisher -> mapper.map(publisher, PublisherResponse.class)).collect(Collectors.toList());
    }
}

package com.example.project1.mapper.impl;

import com.example.project1.entities.Test;
import com.example.project1.mapper.TestMapper;
import com.example.project1.payload.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Destination;

@Service
@RequiredArgsConstructor
public class TestMapperImpl implements TestMapper {
    private final ModelMapper mapper;
    @Override
    public TestDto testToTestDto(Test test) {
        // Define a custom converter to handle the mapping with mismatches
        Converter<Test, TestDto> testConverter = context -> {
            Test source = context.getSource();
            TestDto destination = new TestDto();
            destination.setDtoName(source.getName()); // Handle the mismatched property name
            return destination;
        };

        // Register the custom converter with ModelMapper
        mapper.createTypeMap(Test.class, TestDto.class).setConverter(testConverter);

        // Perform the mapping using ModelMapper
        return mapper.map(test, TestDto.class);
    }
}

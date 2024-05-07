package com.example.project1.mapper;

import com.example.project1.entities.Test;
import com.example.project1.payload.dto.TestDto;

public interface TestMapper {
    TestDto testToTestDto(Test test);
}

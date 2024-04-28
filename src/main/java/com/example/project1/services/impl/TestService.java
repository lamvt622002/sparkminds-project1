package com.example.project1.services.impl;

import com.example.project1.entities.Test;
import com.example.project1.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    public void test(){
        Test test = testRepository.findById(1L).orElseThrow();

        test.setName("test1");

        testRepository.save(test);
    }
}

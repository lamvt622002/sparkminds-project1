package com.example.project1.repository.specs;

import com.example.project1.entities.Test;
import com.example.project1.entities.Test_;
import com.example.project1.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomTestRepository {
    private final TestRepository testRepository;

    private Specification<Test> nameLike(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Test_.NAME), "%"+name+"%");
    }
    public void test(){
        testRepository.findAll(nameLike("Lam"));
        testRepository.findAll(nameLike("Lam"), Pageable.unpaged());
    }
}

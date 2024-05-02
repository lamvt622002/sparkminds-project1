package com.example.project1.repository;

import com.example.project1.entities.Test;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}

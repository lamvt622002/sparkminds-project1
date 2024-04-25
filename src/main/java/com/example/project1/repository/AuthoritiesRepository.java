package com.example.project1.repository;

import com.example.project1.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    Optional<Authorities> findByAuthority(String authority);
}

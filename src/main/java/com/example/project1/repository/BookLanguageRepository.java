package com.example.project1.repository;

import com.example.project1.entities.BookLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLanguageRepository extends JpaRepository<BookLanguage, Long> {
}

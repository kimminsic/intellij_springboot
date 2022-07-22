package com.mysite.sbb.Question.dao;

import com.mysite.sbb.Question.vo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Integer id);
    Page<Question> findAll(Pageable pageable);
}

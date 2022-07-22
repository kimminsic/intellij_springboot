package com.mysite.sbb.Answer.dao;

import com.mysite.sbb.Answer.vo.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findById (Integer id);
}

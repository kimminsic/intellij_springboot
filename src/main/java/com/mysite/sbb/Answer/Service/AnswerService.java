package com.mysite.sbb.Answer.Service;

import com.mysite.sbb.Answer.dao.AnswerRepository;
import com.mysite.sbb.Answer.vo.Answer;
import com.mysite.sbb.Question.dao.QuestionRepository;
import com.mysite.sbb.Question.vo.Question;
import com.mysite.sbb.User.SiteUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository){
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    //answer 생성
    public void create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setModifyDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        answer.setAnswerLike(false);
        question.setViewCount(question.getViewCount()-1);
        questionRepository.save(question);
        answerRepository.save(answer);
    }

    public void setLike(Integer answerId,Integer questionId){
        Answer answer = answerRepository.findById(answerId).get();
        answer.setAnswerLike(!answer.getAnswerLike());
        Question question = questionRepository.findById(questionId).get();
        question.setViewCount(question.getViewCount()-1);
        questionRepository.save(question);
        answerRepository.save(answer);
    }
}

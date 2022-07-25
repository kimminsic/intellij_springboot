package com.mysite.sbb.Answer.Service;

import com.mysite.sbb.Answer.dao.AnswerRepository;
import com.mysite.sbb.Answer.vo.Answer;
import com.mysite.sbb.Question.dao.QuestionRepository;
import com.mysite.sbb.Question.vo.Question;
import com.mysite.sbb.User.SiteUser;
import com.mysite.sbb.Util.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository){
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    //answer 생성
    public Answer create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setModifyDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        answer.setAnswerLike(false);
        answer.setLikeCnt(0);
        question.setViewCount(question.getViewCount()-1);
        questionRepository.save(question);
        answerRepository.save(answer);
        return answer;
    }

    //좋아요
    public void setLike(Integer answerId,Integer questionId){
        Answer answer = answerRepository.findById(answerId).get();
        answer.setAnswerLike(!answer.getAnswerLike());
        if(answer.getAnswerLike()){
            answer.setLikeCnt(answer.getLikeCnt()+1);
        }
        else{
            answer.setLikeCnt(answer.getLikeCnt()-1);
        }
        Question question = questionRepository.findById(questionId).get();
        question.setViewCount(question.getViewCount()-1);
        questionRepository.save(question);
        answerRepository.save(answer);
    }

    //답변 조회
    public Answer getAnswer(Integer id){
        Optional<Answer> answer = this.answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        } else{
            throw new DataNotFoundException("answer not found");
        }
    }

    //답변 수정
    public void modify(Answer answer, String content){
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
}

package com.mysite.sbb.Question.Service;

import com.mysite.sbb.Question.dao.QuestionRepository;
import com.mysite.sbb.Question.vo.Question;
import com.mysite.sbb.User.SiteUser;
import com.mysite.sbb.Util.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> opQuestion = this.questionRepository.findById(id);
        if (opQuestion.isPresent()) {
            Question question = opQuestion.get();
            return question;
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void viewCnt(Integer id){
        Optional<Question> opQuestion = this.questionRepository.findById(id);
        Question question = opQuestion.get();
        question.setViewCount(question.getViewCount()+1);
        questionRepository.save(question);

    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        q.setModifyDate(LocalDateTime.now());
        q.setViewCount(0);
        questionRepository.save(q);
    }

    // 수정
    public void modify(Question question,String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    // 삭제
    public void delete(Question question){
        this.questionRepository.delete(question);
    }

}

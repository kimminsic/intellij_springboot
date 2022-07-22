package com.mysite.sbb.Answer.Controller;

import com.mysite.sbb.Answer.AnswerForm;
import com.mysite.sbb.Answer.Service.AnswerService;
import com.mysite.sbb.Question.Service.QuestionService;
import com.mysite.sbb.Question.vo.Question;
import com.mysite.sbb.User.SiteUser;
import com.mysite.sbb.User.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.zip.DataFormatException;

@Controller
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionService  questionService;
    private final AnswerService answerService;
    private final UserService userService;

    AnswerController(QuestionService questionService, AnswerService answerService, UserService userService){
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) throws DataFormatException {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "question_detail";
        }
        this.answerService.create(question,answerForm.getContent(),siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    @PostMapping("/like/{questionId}/{answerId}")
    public String createAnswer(@PathVariable("answerId") Integer answerId, @PathVariable("questionId") Integer questionId ){
        answerService.setLike(answerId,questionId);
        return String.format("redirect:/question/detail/%s",questionId);
    }
}
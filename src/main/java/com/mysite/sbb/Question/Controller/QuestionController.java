package com.mysite.sbb.Question.Controller;

import com.mysite.sbb.Answer.AnswerForm;
import com.mysite.sbb.Question.QuestionForm;
import com.mysite.sbb.Question.Service.QuestionService;
import com.mysite.sbb.Question.vo.Question;
import com.mysite.sbb.User.SiteUser;
import com.mysite.sbb.User.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.zip.DataFormatException;

@Controller
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    QuestionController(QuestionService questionService, UserService userService){
        this.questionService = questionService;
        this.userService = userService;
    }

    @RequestMapping("list")
    public String showQuestion(Model model , @RequestParam(value="page",defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping("detail/{id}")
    public String showQuestions(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        questionService.viewCnt(id);
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("create")
    public String getQuestionCreate(QuestionForm questionForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("create")
    public String setQuestionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) throws DataFormatException {
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(),questionForm.getContent(),siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id,Principal principal){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question question =this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }
        this.questionService.modify(question,questionForm.getSubject(),questionForm.getContent());
        return String.format("redirect:/question/detail/%s",id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
}

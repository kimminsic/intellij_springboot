package com.mysite.sbb.Question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수입니다.")
    @Size(max=200)
    private String subject;


    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}

package com.mysite.sbb.Answer.vo;

import com.mysite.sbb.Question.vo.Question;
import com.mysite.sbb.User.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    private Boolean answerLike;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    private Integer likeCnt;
}

@Converter
class BooleanToYNConverter implements  AttributeConverter<Boolean,String>{
    @Override
    public String convertToDatabaseColumn(Boolean attribute){
        return (attribute != null && attribute) ? "Y" : "N";
    }
    @Override
    public Boolean convertToEntityAttribute(String dbData){
        return "Y".equals(dbData);
    }
}
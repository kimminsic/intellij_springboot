package com.mysite.sbb;

import com.mysite.sbb.Question.Service.QuestionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.annotations.Test;

@SpringBootApplication
public class SbbApplication {

	private QuestionService questionService;

	SbbApplication(QuestionService questionService) {
		this.questionService = questionService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SbbApplication.class, args);
	}
	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}
}



package com.mysite.sbb.ArticleRepository;


import com.mysite.sbb.vo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitle(String title);
    List<Article> findByBody(String body);

    List<Article> findByTitleAndBody(String title,String body);
}

package com.mysite.sbb.Controller;

import com.mysite.sbb.ArticleRepository.ArticleRepository;
import com.mysite.sbb.Util.Ut;
import com.mysite.sbb.vo.Article;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sbb/article")
@AllArgsConstructor
public class ArticleController {


    private final ArticleRepository articleRepository;


    //article 생성
    @RequestMapping("/Add/")
    @ResponseBody
    public Object doAddArticle(String title, String body){
        Article article = new Article();
        if(Ut.empty(title)){
            return "title을 입력해주세요.";
        }

        if(Ut.empty(body)){
            return "body를 입력해주세요.";
        }
        article.setReg_date(LocalDateTime.now());
        article.setUpdate_date(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        articleRepository.save(article);
        return article;
    }

    //article 다건 모두 조회
    @RequestMapping("/list")
    public String getArticleList(Model model) {
        List<Article> articleList = this.articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        return "article_list";
    }

    //article 단건 조회
    @RequestMapping("/")
    @ResponseBody
    public Object getArticle(long id) {
        Optional<Article> articleItem = articleRepository.findById(id);
        if (articleItem.isEmpty()) {
            return id + "번 article이 존재하지 않습니다.";
        }
        return articleItem;
    }

    //article 항목 조회
    @RequestMapping("/list/")
    @ResponseBody
    public List<Article> getArticleListItem(String title, String body) {

        //article에서 title body 둘다 충족하는 아이템 조회
        if (!Ut.empty(title)&&!Ut.empty(body)) {
            return articleRepository.findByTitleAndBody(title, body);
        }


        //article 에서 title 을 충족하는 아이템 조회
        else if (!Ut.empty(title)) {
            return articleRepository.findByTitle(title);
        }


        //article 에서 body를 충족하는 아이템 조회
        else if (!Ut.empty(body)) {
            return articleRepository.findByBody(body);
        }


        return articleRepository.findAll();
    }

    //article 수정
    @RequestMapping("/Modify/")
    @ResponseBody
    public Object doModifyArticle(long id, String title, String body) {
        Optional<Article> getItem = articleRepository.findById(id);
        if(getItem.isEmpty()){
            return id+"번 article이 존재하지 않습니다.";
        }
        Article getArticleItem = articleRepository.findById(id).get();

        if (!Ut.empty(title)) {
            getArticleItem.setTitle(title);
            getArticleItem.setUpdate_date(LocalDateTime.now());
        }

        if (!Ut.empty(body)) {
            getArticleItem.setBody(body);
            getArticleItem.setUpdate_date(LocalDateTime.now());
        }

        articleRepository.save(getArticleItem);
        return getArticleItem;

    }

    //article 삭제
    @RequestMapping("/Delete/")
    @ResponseBody
    public Object doDeleteArticle(Long id) {
        Optional<Article> getArticle = articleRepository.findById(id);
        if (getArticle.isEmpty()) {
            return id + "번 article이 존재하지 않습니다.";
        }

        articleRepository.deleteById(id);
        return id + "번 article을 삭제하였습니다.";
    }
}

package com.health.item.controller;

import com.health.item.pojo.Article;
import com.health.item.service.IArticleService;
import com.health.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/8 9:35
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @GetMapping("/getarticle")
    public ResponseEntity<List<Article>> getArticleByCid(@RequestParam(value = "cid")Long cid){
        List<Article> articles = this.articleService.getArticle(cid);
        if(CollectionUtils.isEmpty(articles)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/page")
    public ResponseEntity<PageResult<Article>> queryArticleByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "10")Integer rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
            ){
        PageResult<Article> result = this.articleService.queryArticlesByPage(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable(name = "id")Long id){
        Article article = this.articleService.getArticleById(id);
        if(article == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }
}

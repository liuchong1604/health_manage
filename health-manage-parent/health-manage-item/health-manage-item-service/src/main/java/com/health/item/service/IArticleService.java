package com.health.item.service;

import com.health.item.pojo.Article;
import com.health.pojo.PageResult;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/8 9:36
 */
public interface IArticleService {
    /**
     * 根据分类查询文章
     * @param cid
     * @return
     */
    List<Article> getArticle(Long cid);

    /**
     * 分页查询文章实现
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<Article> queryArticlesByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    /**
     * 根据文章id查找文章
     * @param id
     * @return
     */
    Article getArticleById(Long id);
}

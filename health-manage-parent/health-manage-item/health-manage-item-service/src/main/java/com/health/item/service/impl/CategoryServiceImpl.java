package com.health.item.service.impl;

import com.health.item.mapper.CategoryMapper;
import com.health.item.mapper.IArticleMapper;
import com.health.item.pojo.Article;
import com.health.item.pojo.Category;
import com.health.item.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/3/20 21:09
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IArticleMapper articleMapper;

    @Override
    public List<Category> queryCategoryByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long id) {
//        Article article = new Article();
//        article.setCategoryId(id);
//        System.out.println(article.getCategoryId());
//        List<Article> articles = this.articleMapper.select(article);
//        for(Article article1 :articles){
//            this.articleMapper.delete(article1);
//        }
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",id);
        this.articleMapper.deleteByExample(example);

        Category record = new Category();
        record.setId(id);
        return this.categoryMapper.delete(record);
    }
}

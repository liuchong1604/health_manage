package com.health.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.item.mapper.IArticleMapper;
import com.health.item.pojo.Article;
import com.health.item.service.IArticleService;
import com.health.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/8 9:37
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleMapper articleMapper;

    @Override
    public List<Article> getArticle(Long cid) {
        Article record = new Article();
        record.setCategoryId(cid);
        return this.articleMapper.select(record);
    }

    /**
     * 根据分页条件查询文章
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Article> queryArticlesByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        //根据key字段模糊查询
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%" + key + "%");
        }
        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序条件
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc? "desc":"asc"));
        }
        List<Article> articles = this.articleMapper.selectByExample(example);
        //包装成pageInfo对象
        PageInfo pageInfo = new PageInfo<>(articles);
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public Article getArticleById(Long id) {
        Article record = new Article();
        record.setId(id);
        return this.articleMapper.selectOne(record);
    }
}

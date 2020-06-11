package com.health.item.mapper;

import com.health.item.pojo.Article;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/8 9:35
 */
@Component
public interface IArticleMapper extends Mapper<Article> {
}

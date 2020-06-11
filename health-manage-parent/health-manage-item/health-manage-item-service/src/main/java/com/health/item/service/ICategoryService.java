package com.health.item.service;

import com.health.item.pojo.Category;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/3/20 21:09
 */
public interface ICategoryService {

    /**
     * 根据父节点的id查询子节点
     * @param pid
     * @return
     */
    List<Category> queryCategoryByPid(Long pid);

    /**
     * 删除分类
     * @param id
     * @return
     */
    Integer delete(Long id);
}

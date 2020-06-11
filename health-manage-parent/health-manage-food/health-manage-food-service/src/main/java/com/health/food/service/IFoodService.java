package com.health.food.service;

import com.health.food.pojo.Food;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 8:55
 */
public interface IFoodService {
    /**
     * 查询所有食物列表
     * @return
     */
    List<Food> findAll();

    /**
     * 根据分类id查询相关食物
     * @param tid
     * @return
     */
    List<Food> getFoodByCid(Integer tid);

    /**
     * 获取数据库数量
     * @return
     */
    Integer getCount();

    /**
     * 获取数量
     * @return
     */
    Integer getTypeCount();

    /**
     * 根据食物名称查询食物列表
     * @param names
     * @return
     */
    List<Food> findByNames(String[] names);

    /**
     * 根据食物id查找食物
     * @param id
     * @return
     */
    List<Food> findByIds(Long[] ids);
}

package com.health.food.service.impl;

import com.health.food.mapper.IFoodMapper;
import com.health.food.pojo.Food;
import com.health.food.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 8:56
 */
@Service
public class FoodServiceImpl implements IFoodService {
    @Autowired
    private IFoodMapper foodMapper;

    @Override
    public List<Food> findAll() {
        return this.foodMapper.selectAll();
    }

    @Override
    public List<Food> getFoodByCid(Integer tid) {
        Food record = new Food();
        record.setTypeId(tid);
        return this.foodMapper.select(record);
    }

    @Override
    public Integer getCount() {
        return this.foodMapper.getCount();
    }

    @Override
    public Integer getTypeCount() {
        return this.foodMapper.getTypeCount();
    }

    @Override
    public List<Food> findByNames(String[] names) {
        List<Food> foods = new ArrayList<>();
        for(int i=0;i<names.length;i++){
            Example example = new Example(Food.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("foodname",names[i]);
            foods.add(this.foodMapper.selectOneByExample(example));
        }
        return foods;
    }

    @Override
    public List<Food> findByIds(Long[] ids) {
        List<Food> foods = new ArrayList<>();
        for(Long id :ids){
            Food food = new Food();
            food.setId(id);
            foods.add(this.foodMapper.selectOne(food));
        }
        return foods;
    }
}

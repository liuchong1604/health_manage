package com.health.food.mapper;

import com.health.food.pojo.Food;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 8:55
 */
@Component
public interface IFoodMapper extends Mapper<Food> {

    /**
     * 查询数据库总数
     * @return
     */
    @Select("select count(*) from tb_food")
    Integer getCount();

    /**
     * 查询数据库总数
     * @return
     */
    @Select("select count(*) from tb_type")
    Integer getTypeCount();
}

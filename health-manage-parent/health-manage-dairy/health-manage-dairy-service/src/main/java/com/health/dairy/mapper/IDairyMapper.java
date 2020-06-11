package com.health.dairy.mapper;

import com.health.dairy.pojo.Dairy;
import com.health.dairy.pojo.FoodDairy;
import com.health.food.pojo.Food;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 10:23
 */
@Component
public interface IDairyMapper extends Mapper<Dairy>{

    /**
     * 插入日志食物中间表
     * @param did
     * @param fid
     * @param heat
     */
    @Insert("insert into tb_food_dairy(dairy_id,food_id) values(#{did},#{fid})")
    void insertDairyAndFood(@Param("did")Long did, @Param("fid")Long fid);

    /**
     * 根据did查询食物
     * @param did
     * @return
     */
    @Select("select * from tb_food a inner join tb_food_dairy b on a.id = b.food_id and b.dairy_id = #{did}")
    List<Food> findFoods(Long did);

    /**
     * 删除中间表数据
     * @param did
     * @return
     */
    @Delete("delete from tb_food_dairy where dairy_id = #{did}")
    Integer deleteFoodAndDairy(Long did);
}

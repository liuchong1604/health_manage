package com.health.dairy.service;

import com.health.dairy.bo.DairyBo;
import com.health.dairy.pojo.Dairy;
import com.health.food.pojo.Food;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 10:23
 */
public interface IDairyService {
    /**
     * 根据用户查询日志
     * @param uid
     * @return
     */
    List<Dairy> findAllByUid(Long uid);

    /**
     * 根据日期查找日志
     * @param dates
     * @return
     */
    List<Dairy> findByCreatedtimes(String[] dates);

    /**
     * 保存日志
     * @param dairyBo,uid
     */
    void saveDairy(DairyBo dairyBo);

    /**
     * 根据单个时间查找日志
     * @param date
     * @return
     */
    Dairy findByCreatedtime(String date);

    /**
     * 根据日志id查找食物
     * @param did
     * @return
     */
    List<Food> findFoods(Long did);

    /**
     * 删除日志
     * @param createdtime
     * @return
     */
    Integer delete(String createdtime);

    /**
     * 更新日志
     * @param dairyBo
     */
    void updateDairy(DairyBo dairyBo);
}

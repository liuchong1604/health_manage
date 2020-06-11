package com.health.dairy.pojo;

import javax.persistence.Table;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/8 9:22
 */
@Table(name = "tb_food_dairy")
public class FoodDairy {
    private Long dairyId;
    private Long foodId;

    public Long getDairyId() {
        return dairyId;
    }

    public void setDairyId(Long dairyId) {
        this.dairyId = dairyId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }
}

package com.health.dairy.bo;

import com.health.dairy.pojo.Dairy;
import com.health.food.pojo.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/6 22:26
 */
public class DairyBo {
    private Dairy dairy;
    private List<Food> food ;

    public Dairy getDairy() {
        return dairy;
    }

    public void setDairy(Dairy dairy) {
        this.dairy = dairy;
    }

    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }
}

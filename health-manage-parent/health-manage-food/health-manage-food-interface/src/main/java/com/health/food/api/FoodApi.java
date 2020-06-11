package com.health.food.api;

import com.health.food.pojo.Food;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/6 23:06
 */
@RequestMapping("/food")
public interface FoodApi {
    @GetMapping("/all")
    public ResponseEntity<List<Food>> findAll();

    @GetMapping("/getByNames")
    ResponseEntity<List<Food>> findByNames(@RequestParam("name")String[] names);

    @GetMapping("/getByIds")
    public ResponseEntity<List<Food>> findByIds(@RequestParam(name = "ids")Long[] ids);
}

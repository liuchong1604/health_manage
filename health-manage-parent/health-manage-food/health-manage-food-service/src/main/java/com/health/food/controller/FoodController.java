package com.health.food.controller;

import com.health.food.pojo.Food;
import com.health.food.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 8:55
 */
@Controller
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    @GetMapping("/all")
    public ResponseEntity<List<Food>> findAll() {
        List<Food> foods = this.foodService.findAll();
        if (CollectionUtils.isEmpty(foods)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{cid}")
    public ResponseEntity<List<Food>> getFoodsByCid(@PathVariable(name = "cid") Integer cid) {
        List<Food> foods = this.foodService.getFoodByCid(cid);
        if (CollectionUtils.isEmpty(foods)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foods);
    }

    /**
     * 推荐食物----算法思路: 使用贪心算法尽可能多的获得能量，一天吃12分食物，食物共有15种(可从数据库添加）,食物种类有四种，每一种可以吃k份
     *
     * @param heat
     * @return
     */
    @PostMapping("/recommend")
    public ResponseEntity<Map<Integer,Map<Integer,List<String>>>> RecommendedDietary(@RequestParam(name = "heat") Float heat) {
        int m = 12;     // 一天可以吃12份
        int foodCount = this.foodService.getCount(); // 食物数量
        int typeCount = this.foodService.getTypeCount(); // 食物种类
        int[] k = {0, 3, 2, 5, 6};    // 每一类可以吃的份数
        int cnt = 0; // 当前吃的份数
        float sum = 0; // 当前吃的热量总和
        List<Food> foods = this.foodService.findAll();
        Map<Food, Float> map = new HashMap<>();
        for (Food food : foods) {
            map.put(new Food(food.getFoodname(), food.getTypeId()), food.getHeat());
        }
        List<Map.Entry<Food, Float>> list = sort(map);
        List<String> food = new ArrayList<>();
        for (Map.Entry<Food, Float> entry : list) {
            if (k[entry.getKey().getTypeId()] <= 0) {
                continue;
            }
            sum += entry.getValue();
            if (sum > heat) {
                continue;
            }
            food.add(entry.getKey().getFoodname());
            cnt++;
            if (cnt == m) {
                break;
            }
            k[entry.getKey().getTypeId()]--;
        }
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        boolean[] bool = new boolean[12];
        int rand;
        for (int j = 0; j < 12; j++) {
            do {
                rand = (int) (Math.random() * (12));
            } while (bool[rand]);
            bool[rand] = true;
            if (list1.size() < 4) {
                list1.add(food.get(rand));
            } else if (list2.size() < 4) {
                list2.add(food.get(rand));
            } else if (list3.size() < 4) {
                list3.add(food.get(rand));
            }
        }
        List<List<String>> listList = new ArrayList<>();
        listList.add(list1);
        listList.add(list2);
        listList.add(list3);
        boolean[] bool2 = null;
        Map<Integer,List<String>> map1 = null;
        Map<Integer,Map<Integer,List<String>>> map2 = new HashMap<>();
        for(int i=0;i<7;i++){
            map1 = new HashMap<>();
            bool2 = new boolean[3];
            for(int j=0;j<3;j++) {
                do {
                    rand = (int) (Math.random() * (3));
                } while (bool2[rand]);
                bool2[rand]=true;
                map1.put(j,listList.get(rand));
            }
            map2.put(i,map1);
        }
//        for(Map.Entry<Integer,Map<Integer,List<String>>> a:map2.entrySet()){
//            System.out.println(a.getKey()+ "and" + a.getValue() + "\n");
//        }       // 遍历map数组
        return ResponseEntity.ok(map2);
    }

    private List<Map.Entry<Food, Float>> sort(Map map) {
        List<Map.Entry<Food, Float>> list = new ArrayList<Map.Entry<Food, Float>>(map.entrySet());
//        Collections.sort(list, (Map.Entry<Food, Float> o1, Map.Entry<Food, Float> o2) -> {
//                 return o1.getValue().compareTo(o2.getValue());
//        });
        Collections.sort(list, Comparator.comparing(Map.Entry<Food, Float>::getValue)); // lambda表达式这里看不懂

        return list;
    }

    @GetMapping("/getByNames")
    public ResponseEntity<List<Food>> findByNames(@RequestParam("name")String[] names){
        List<Food> foods = this.foodService.findByNames(names);
        if(CollectionUtils.isEmpty(foods)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/getByIds")
    public ResponseEntity<List<Food>> findByIds(@RequestParam(name = "ids")Long[] ids){
        List<Food> foods = this.foodService.findByIds(ids);
        if(CollectionUtils.isEmpty(foods)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foods);
    }
}

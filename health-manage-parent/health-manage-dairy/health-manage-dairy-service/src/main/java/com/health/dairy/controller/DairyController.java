package com.health.dairy.controller;

import com.health.dairy.bo.DairyBo;
import com.health.dairy.pojo.Dairy;
import com.health.dairy.service.IDairyService;
import com.health.food.pojo.Food;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 10:22
 */
@Controller
@RequestMapping("/dairy")
public class DairyController {
    @Autowired
    private IDairyService dairyService;

    @GetMapping("/all")
    public ResponseEntity<List<Dairy>> findAllByUid(@RequestParam(name = "uid") Long uid) {
        List<Dairy> dairies = this.dairyService.findAllByUid(uid);
        if (CollectionUtils.isEmpty(dairies)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dairies);
    }

    @GetMapping("/getByTime")
    public ResponseEntity<Dairy> findByCreatedtime(@RequestParam(name = "createdtime")String date){
        Dairy dairy = this.dairyService.findByCreatedtime(date);
        if(dairy == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dairy);
    }
    @GetMapping("/getByTimes")
    public ResponseEntity<List<Dairy>> findByCreatedtimes(@RequestParam(name = "createdtime") String[] dates) {
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        Date[] date1 = new Date[dates.length];
//        // 错误创建对象数组方法
//        // Dairy[] dairy = new Dairy[dates.length];       没有调用Student的构造方法,相当于只有申明，没有初始化
//        Dairy[] dairies = new Dairy[dates.length];
//        for(int i=0;i<dates.length;i++){
//            dairies[i] = new Dairy();
//        }
//        try {
//            for(int i=0;i<dates.length;i++) {
//                //使用SimpleDateFormat的parse()方法生成Date
//                date1[i] = sf.parse(dates[i]);
//                dairies[i].setCreatedtime(new java.sql.Date(date1[i].getTime()));
//                System.out.println(dairies[i].getCreatedtime());
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
          List<Dairy> dairies = this.dairyService.findByCreatedtimes(dates);
          if(CollectionUtils.isEmpty(dairies)){
              return ResponseEntity.notFound().build();
          }
        return ResponseEntity.ok(dairies);
    }

    @PostMapping("/saveDairy")
    public ResponseEntity<Void> saveDairy(@RequestBody DairyBo dairyBo){
//        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
//        System.out.println(new Date(dairyBo.getCreatedtime().getTime()));
//        System.out.println(dairyBo.getCreatedtime());
        this.dairyService.saveDairy(dairyBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/updateDairy")
    public ResponseEntity<Void> updateDairy(@RequestBody DairyBo dairyBo){
        this.dairyService.updateDairy(dairyBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/getFoods")
    public ResponseEntity<List<Food>> findFoods(@RequestParam(name = "did")Long did){
        List<Food> foods = this.dairyService.findFoods(did);
        if(CollectionUtils.isEmpty(foods)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam(name = "createdtime")String createdtime){
        Integer num = this.dairyService.delete(createdtime);
        if(num == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

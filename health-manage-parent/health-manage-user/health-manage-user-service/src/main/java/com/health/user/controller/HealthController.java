package com.health.user.controller;

import com.health.user.bo.EvaluateBo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/2 16:53
 */
@Controller
@RequestMapping("/health")
public class HealthController {
    @PostMapping("/evaluate")
    public ResponseEntity<Integer> getResults(EvaluateBo evaluate) {
        int result = 0;
        if ("男".equals(evaluate.getSex())) {
            result += 2;
        } else {
            result += 1;
        }
        if (evaluate.getAge() <= 39) {
            result += 0;
        } else if (evaluate.getAge() > 39 && evaluate.getAge() <= 44) {
            result += 1;
        } else if (evaluate.getAge() > 44 && evaluate.getAge() <= 49) {
            result += 2;
        } else if (evaluate.getAge() > 49 && evaluate.getAge() <= 54) {
            result += 3;
        } else if (evaluate.getAge() > 54) {
            result += 4;
        }
        if (evaluate.getSystolic() <= 120) {
            result -= 2;
        } else if (evaluate.getSystolic() > 120 && evaluate.getSystolic() <= 130) {
            result += 0;
        } else if (evaluate.getSystolic() > 130 && evaluate.getSystolic() <= 140) {
            result += 1;
        } else if (evaluate.getSystolic() > 140 && evaluate.getSystolic() <= 160) {
            result += 2;
        } else if (evaluate.getSystolic() > 160 && evaluate.getSystolic() <= 180) {
            result += 5;
        } else if (evaluate.getSystolic() > 180) {
            result += 8;
        }
        if(evaluate.getBMI() <= 24){
            result += 0;
        }else if(evaluate.getBMI() > 24 && evaluate.getBMI() <= 28){
            result += 1;
        }else if(evaluate.getBMI() > 28){
            result += 2;
        }
        if(evaluate.getCholesterol() <= 5.2){
            result += 0;
        }else if(evaluate.getCholesterol() > 5.2){
            result += 1;
        }
        if(evaluate.getSmoke() == true){
            result += 2;
        }else{
            result += 1;
        }
        if(evaluate.getDiabetes() == true){
            result += 1;
        }else{
            result += 0;
        }
        if(evaluate.getFamily() == true){
            result += 1;
        }else{
            result += 0;
        }
        return ResponseEntity.ok(result);
    }
}

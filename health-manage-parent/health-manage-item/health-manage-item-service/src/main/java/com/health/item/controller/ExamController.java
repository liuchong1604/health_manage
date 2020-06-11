package com.health.item.controller;

import com.health.item.pojo.Examination;
import com.health.item.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/3 21:28
 */
@Controller
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private IExamService examService;

    @GetMapping("/all")
    public ResponseEntity<List<Examination>> findAllByUid(@RequestParam(name = "uid")Long uid){
        List<Examination> examinations = this.examService.findAllByUid(uid);
        if(CollectionUtils.isEmpty(examinations)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(examinations);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Examination>> findByUid(@PathVariable(name = "id")String id, @RequestParam(name="uid")Long uid){
        List<Examination> examinations = this.examService.findByUid(id,uid);
        if(CollectionUtils.isEmpty(examinations)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(examinations);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Examination> SelectIdAndExamedtime(@PathVariable(name = "id")Long id){
        Examination examination = this.examService.SelectIdAndExamedtime(id);
        return ResponseEntity.ok(examination);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Examination>> findAll(){
        List<Examination> examinations = this.examService.findAll();
        if(CollectionUtils.isEmpty(examinations)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(examinations);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addExam(@RequestBody List<Examination> exams) throws ParseException {
        System.out.println("成功获取到数据");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for(Examination examination:exams){
            String strDate = sf.format(examination.getExamedtime());
            Date newDate = sf.parse(strDate);
            java.sql.Date result = new java.sql.Date(newDate.getTime());
            examination.setExamedtime(result);
        }
        Integer num = this.examService.addExam(exams);
        if(num == 0){
            return ResponseEntity.badRequest().build();
        }
       return ResponseEntity.ok().build();
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> deleteExcelData(@RequestParam(name = "examedtimes")String[] examedtimes){
        System.out.println("成功获取到数据");
        for(String examedtime:examedtimes){
            System.out.println(examedtime);
        }
        Integer num = this.examService.deleteExcelData(examedtimes);
        if(num == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}

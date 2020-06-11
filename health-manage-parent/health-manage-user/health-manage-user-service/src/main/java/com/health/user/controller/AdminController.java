package com.health.user.controller;

import com.health.user.pojo.Admin;
import com.health.user.service.IAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/17 17:11
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @PostMapping("/adminlogin")
    public ResponseEntity<Map<String,String>> login(@RequestParam(name = "username")String username,@RequestParam(name = "password")String password){
        System.out.println(username);
        String token = this.adminService.login(username,password);
        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("role","admin");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/adminRegister")
    public ResponseEntity<Void> register(Admin admin){
        Boolean boo = this.adminService.register(admin);
        if(boo == null || !boo){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/query")
//    public ResponseEntity<Admin> queryAdmin(String username,String password){
//        Admin admin = this.adminService.queryAdmin(username,password);
//        if(admin == null){
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(admin);
//    }

}

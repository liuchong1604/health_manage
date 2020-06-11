package com.health.user.controller;

import com.health.pojo.PageResult;
import com.health.user.pojo.User;
import com.health.user.service.IUserService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/25 13:58
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam(name = "phone")String phone) {
        System.out.println(phone);
        Boolean boo = this.userService.sendVerifyCode(phone);
        if (boo == null || !boo) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam(name = "code") String code) {
          Integer number = this.userService.register(user,code);
          if(number == 1){
              return ResponseEntity.status(HttpStatus.CONFLICT).build();     //409昵称已被占用
          }else if(number == 2){
              return ResponseEntity.status(HttpStatus.FOUND).build();        //302手机号被占用
          }else if(number == 3){
              return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();     //424验证码错误
          }else if(number == 5){
              return ResponseEntity.badRequest().build();     //400注册失败,重新尝试（用户名或密码错误）
          }else{
              return ResponseEntity.status(HttpStatus.CREATED).build();   //201成功
          }
//        Boolean bool = this.userService.register(user, code);
//        if (bool == null || !bool) {
//            return ResponseEntity.badRequest().build();
//        }
//        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/userlogin")
    public ResponseEntity<Map<String,String>> login(@RequestParam(name = "username")String username, @RequestParam(name = "password")String password,@RequestParam(name = "expire",required = false)Integer expire){
        String token = this.userService.login(username,password,expire);
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(token)){
            map.put("msg","用户名或者密码错误!");
           return ResponseEntity.ok(map);
        }
        map.put("token",token);
        map.put("role","user");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<User> getUserInfo(@RequestParam(name = "token")String token){
        User user = this.userService.findUserInfo(token);
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/page")
    public ResponseEntity<PageResult<User>> queryUserByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "10")Integer rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
    ){
        PageResult<User> result = this.userService.queryUsersByPage(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id){
        Integer num = this.userService.delete(id);
        if(num == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getById")
    public ResponseEntity<User> getById(@RequestParam(name = "id")Long id){
        User user = this.userService.getById(id);
        if(user == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/getByPhone")
    public ResponseEntity<User> getByPhone(@RequestParam(name="phone")String phone){
        User user = this.userService.getByPhone(phone);
        if(user == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping("/forgetPwd")
    public  ResponseEntity<Void> forgetPwd(String phone,String password){
        int num = this.userService.forgetPwd(phone,password);
        if(num != 1){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify")
    public ResponseEntity<Void> modifyUser(@RequestParam(name = "sex")String sex,@RequestParam(name = "birth")String birth,@RequestParam(name = "email")String email,@RequestParam(name = "uid")Long uid){
        int num = this.userService.modify(sex,birth,email,uid);
        if(num == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify")
    public  ResponseEntity<Void> verify(@RequestParam(name = "phone") String phone,@RequestParam(name = "code")String code){
        int num = this.userService.verify(phone,code);
        if(num == 0){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/modifyPwd")
    public ResponseEntity<Map<String,String>> modifyPwd(@RequestParam(name = "oldPass")String oldPass,@RequestParam(name="phone")String phone,@RequestParam(name = "pass")String pass,@RequestParam(name = "code")String code){
        int num = this.userService.modifyPwd(oldPass,phone,pass,code);
        Map<String,String> map = new HashMap<>();
        if(num == 0){
            map.put("msg","0");
            return ResponseEntity.ok(map);        // 0 验证码错误
        }else if(num == 2){
            map.put("msg","2");
            return ResponseEntity.ok(map);           // 2 密码错误
        }
        map.put("msg","1");
        return ResponseEntity.ok(map);
    }
}

package com.health.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/17 16:47
 */
@Table(name = "tb_admin")
@Data
@ToString
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 4,max = 30,message = "用户名必须在4～30之间")
    private String username;

    @JsonIgnore    //对象序列化为json字符串时,忽略该属性
    @Length(min=4,max=30,message="密码必须在6~20位之间")
    private String password;

    @JsonIgnore
    private String salt;
}
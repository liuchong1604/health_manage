package com.leyou.test;

import com.health.user.UserServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/29 15:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        this.redisTemplate.opsForValue().set("key1","value1");

        String val = this.redisTemplate.opsForValue().get("key1").toString();
        System.out.println(val);
    }

}

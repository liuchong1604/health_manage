package com.health.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.health.pojo.PageResult;
import com.health.user.config.JwtProperties;
import com.health.user.mapper.IUserMapper;
import com.health.user.pojo.User;
import com.health.user.service.IUserService;
import com.health.user.utils.CodeUtils;
import com.health.user.utils.JwtUtils;
import com.health.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/25 13:58
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    public static final String REDIS_RANDOM_CODE_PREFIX = "RANDOM_CODE_USER_";

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @Override
    public Boolean sendVerifyCode(String phone) {
        String code = NumberUtils.generateCode(6);
        System.out.println(code);
        try {
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.amqpTemplate.convertAndSend("health.sms.exchange", "sms.verify.code", msg);
            //将code存入redis
            this.redisTemplate.opsForValue().set(REDIS_RANDOM_CODE_PREFIX + phone, code, 1, TimeUnit.MINUTES);
            return true;
        } catch (AmqpException e) {
            logger.error("发送短信失败。phone: {} ,code: {}", phone, code);
            return false;
        }
    }

    @Override
    public Integer register(User user, String code) {
        User record = new User();
        record.setUsername(user.getUsername());
        User user1 = this.userMapper.selectOne(record);
        if (user1 != null) {
            return 1;
        }
        User record1 = new User();
        record1.setPhone(user.getPhone());
        User user2 = this.userMapper.selectOne(record1);
        if (user2 != null) {
            return 2;
        }
        String cacheCode = this.redisTemplate.opsForValue().get(REDIS_RANDOM_CODE_PREFIX + user.getPhone());
        if (!StringUtils.equals(cacheCode, code)) {
            return 3;
        }
        //生成盐
        String salt = CodeUtils.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodeUtils.md5Hex(user.getPassword(), salt));

        user.setId(null);
        user.setCreated(new Date());
        Boolean boo = this.userMapper.insertSelective(user) == 1;
        if (boo) {
            this.redisTemplate.delete(REDIS_RANDOM_CODE_PREFIX + user.getPhone());
            return 4;
        }
        return 5;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param expire
     * @return
     */
    @Override
    public String login(String username, String password, Integer expire) {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);

        if (user == null) {
            return null;
        }

        password = CodeUtils.md5Hex(password, user.getSalt());
        System.out.println(password);
        if (StringUtils.equals(password, user.getPassword())) {
            try {
                Long id = user.getId();
                Integer expireTime;
                if(expire == 0){
                    expireTime = this.jwtProperties.getExpire();
                }else{
                    expireTime = 10080;          //选择记住我有7天有效时间
                }
                return JwtUtils.generateToken(id, username, this.jwtProperties.getPrivateKey(),expireTime);

            } catch (Exception e) {
                logger.error("登录失败,username:{},password:{}", username, password);
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User findUserInfo(String token) {
        try {
            Long id = (Long)JwtUtils.getInfoFromToken(token,this.jwtProperties.getPublicKey()).get("id");
            System.out.println(id);
            String username = (String)JwtUtils.getInfoFromToken(token,this.jwtProperties.getPublicKey()).get("username");
            System.out.println(username);
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PageResult<User> queryUsersByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("username","%" + key + "%");
        }

        PageHelper.startPage(page,rows);

        if(StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<User> users = this.userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public Integer delete(Long id) {
        User record = new User();
        record.setId(id);
        return this.userMapper.delete(record);
    }

    @Override
    public User getById(Long id) {
        User record = new User();
        record.setId(id);
        return this.userMapper.selectOne(record);
    }

    @Override
    public User getByPhone(String phone) {
        User record = new User();
        record.setPhone(phone);
        return this.userMapper.selectOne(record);
    }

    @Override
    public int forgetPwd(String phone,String password) {
        System.out.println(phone + "|" + password);
        User record = new User();
        String salt = CodeUtils.generateSalt();
        System.out.println(salt);
        record.setPassword(CodeUtils.md5Hex(password,salt));
        record.setSalt(salt);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone",phone);
        Boolean boo = this.userMapper.updateByExampleSelective(record,example) == 1;
        if(boo){
            this.redisTemplate.delete(REDIS_RANDOM_CODE_PREFIX + phone);
            return 1;
        }
        return 2;
    }

    @Override
    public int modify(String sex, String birth, String email,Long uid) {
        User record = new User();
        record.setSex(sex);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sf.parse(birth);
            record.setBirth(new java.sql.Date(date.getTime()));
            record.setEmail(email);
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",uid);
            return this.userMapper.updateByExampleSelective(record,example);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int verify(String phone,String code) {
        String cacheCode = this.redisTemplate.opsForValue().get(REDIS_RANDOM_CODE_PREFIX + phone);
        if(!StringUtils.equals(cacheCode,code)){
            return 0;
        }
        return 1;
    }

    @Override
    public int modifyPwd(String oldPass, String phone, String pass, String code) {
        // 1. 判断验证码是否正确
        String cacheCode = this.redisTemplate.opsForValue().get(REDIS_RANDOM_CODE_PREFIX + phone);
        if(!StringUtils.equals(cacheCode,code)){          // 验证码错误
            return 0;
        }
        // 2. 判断旧密码是否正确
        User record = new User();
        record.setPhone(phone);
        User user = this.userMapper.selectOne(record);
        if(!StringUtils.equals(user.getPassword(),CodeUtils.md5Hex(oldPass,user.getSalt()))){    // 密码错误
            return 2;
        }
        // 3.根据手机号修改密码
        User record1 = new User();
        String salt = CodeUtils.generateSalt();
        record1.setPassword(CodeUtils.md5Hex(pass,salt));
        record1.setSalt(salt);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone",phone);
        Boolean boo = this.userMapper.updateByExampleSelective(record1,example) == 1;
        if(boo){
            this.redisTemplate.delete(REDIS_RANDOM_CODE_PREFIX + phone);
            return 1;
        }
        return 2;
    }


}

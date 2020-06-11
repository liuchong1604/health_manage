package com.health.user.service.impl;

import com.health.user.config.JwtProperties;
import com.health.user.mapper.AdminMapper;
import com.health.user.pojo.Admin;
import com.health.user.service.IAdminService;
import com.health.user.utils.CodeUtils;
import com.health.user.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/17 17:21
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JwtProperties jwtProperties;

    private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private static final String REDIS_RANDOM_CODE_PREFIX = "RANDOM_CODE_ADMIN_";

//    @Override
//    public Admin queryAdmin(String username, String password) {
//        Admin record = new Admin();
//        record.setUsername(username);
//        Admin admin = this.adminMapper.selectOne(record);
//
//        if(admin == null){
//            return null;
//        }
//        password = CodeUtils.md5Hex(password,admin.getSalt());
//
//        if(StringUtils.equals(password,admin.getPassword())){
//            return admin;
//        }
//        return null;
//    }

    @Override
    public String login(String username, String password) {
        Admin record = new Admin();
        record.setUsername(username);
        Admin admin = this.adminMapper.selectOne(record);

        if(admin == null){
            return null;
        }
        password = CodeUtils.md5Hex(password,admin.getSalt());
        System.out.println(password);
        if(StringUtils.equals(password,admin.getPassword())){
            try{
                Long id = admin.getId();
                username = admin.getUsername();
                return JwtUtils.generateToken(id,username,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire());
            }catch(Exception e){
                logger.error("登录失败,username:{},password:{}",username,password);
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Boolean register(Admin admin) {
        String salt = CodeUtils.generateSalt();
        admin.setSalt(salt);
        admin.setPassword(CodeUtils.md5Hex(admin.getPassword(),salt));
        admin.setId(null);
        boolean boo = this.adminMapper.insertSelective(admin) == 1;
        return boo;
    }
}

package com.health.user.service;

import com.health.pojo.PageResult;
import com.health.user.pojo.User;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/25 13:58
 */
public interface IUserService {
    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    Boolean sendVerifyCode(String phone);

    /**
     * 注册
     * @param user
     * @param code
     * @return
     */
    Integer register(User user,String code);

    /**
     * 登录
     * @param username
     * @param password
     * @param expire
     * @return
     */
    String login(String username, String password,Integer expire);

    /**
     * 根据token查找用户信息
     * @param token
     * @return
     */
    User findUserInfo(String token);

    /**
     * 分页查询用户
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<User> queryUsersByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    User getByPhone(String phone);

    /**
     * 忘记密码
     * @param phone
     * @param password
     * @return
     */
    int forgetPwd(String phone,String password);

    /**
     * 修改个人信息
     * @param sex
     * @param birth
     * @param email
     * @param uid
     * @return
     */
    int modify(String sex, String birth, String email,Long uid);

    /**
     * 验证码是否正确
     * @param phone
     * @param code
     * @return
     */
    int verify(String phone,String code);

    /**
     * 修改密码
     * @param oldPass
     * @param phone
     * @param pass
     * @param code
     * @return
     */
    int modifyPwd(String oldPass, String phone, String pass, String code);
}

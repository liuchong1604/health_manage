package com.health.user.service;

import com.health.user.pojo.Admin;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/2/17 17:21
 */
public interface IAdminService {
//    /**
//     * 查询管理员
//     * @param username
//     * @param password
//     * @return
//     */
//    Admin queryAdmin(String username,String password);

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 注册
     * @param admin
     * @return
     */
    Boolean register(Admin admin);
}

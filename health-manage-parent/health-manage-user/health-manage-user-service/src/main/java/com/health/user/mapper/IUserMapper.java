package com.health.user.mapper;

import com.health.user.pojo.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/3/5 8:58
 */
@Component
public interface IUserMapper extends Mapper<User> {
}

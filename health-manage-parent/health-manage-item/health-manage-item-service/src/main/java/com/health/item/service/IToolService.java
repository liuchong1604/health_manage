package com.health.item.service;

import com.health.item.pojo.Tool;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/18 8:48
 */
public interface IToolService {
    /**
     * 获取所有工具
     * @return
     */
    List<Tool> getTools();

    /**
     * 根据id工具
     * @param id
     * @return
     */
    Tool getToolById(Integer id);
}

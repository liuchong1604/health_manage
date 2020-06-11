package com.health.item.service.impl;

import com.health.item.mapper.IToolMapper;
import com.health.item.pojo.Tool;
import com.health.item.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/18 8:48
 */
@Service
public class ToolServiceImpl implements IToolService {
    @Autowired
    private IToolMapper toolMapper;

    @Override
    public List<Tool> getTools() {
        return this.toolMapper.selectAll();
    }

    @Override
    public Tool getToolById(Integer id) {
        Tool record = new Tool();
        record.setId(id);
        return this.toolMapper.selectOne(record);
    }
}

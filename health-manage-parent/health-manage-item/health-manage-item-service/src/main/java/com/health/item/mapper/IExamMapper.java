package com.health.item.mapper;

import com.health.item.pojo.Examination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/3 21:29
 */
@Component
public interface IExamMapper extends Mapper<Examination> {
    @SelectProvider(type = ExamSelectProvider.class,method = "selectIdAndExamedtime")
    public Examination selectIdAndExamedtime(@Param("id")Long id);
}

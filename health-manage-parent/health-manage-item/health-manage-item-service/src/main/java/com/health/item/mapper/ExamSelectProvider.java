package com.health.item.mapper;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/3 21:59
 */
public class ExamSelectProvider {
    public String selectIdAndExamedtime(Long id){
        return "select id,examedtime from tb_exam where id = " + id;
    }
}

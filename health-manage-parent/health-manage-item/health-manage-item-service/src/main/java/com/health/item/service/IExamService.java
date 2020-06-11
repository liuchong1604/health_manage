package com.health.item.service;

import com.health.item.pojo.Examination;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/3 21:30
 */
public interface IExamService {

    /**
     * 测试查询指定列
     * @param id
     * @return
     */
    Examination SelectIdAndExamedtime(Long name);

    /**
     * 根据用户id查询指定的列
     * @param id
     * @param uid
     * @return
     */
    List<Examination> findByUid(String id, Long uid);

    /**
     * 根据用户id查询所有体检数据
     * @param uid
     * @return
     */
    List<Examination> findAllByUid(Long uid);

    /**
     * 查找所有体检数据
     * @return
     */
    List<Examination> findAll();

    /**
     * 插入多列体检数据
     * @param exams
     * @return
     */
    Integer addExam(List<Examination> exams);

    /**
     * 删除体检数据
     * @param examedtimes
     * @return
     */
    Integer deleteExcelData(String[] examedtimes);
}

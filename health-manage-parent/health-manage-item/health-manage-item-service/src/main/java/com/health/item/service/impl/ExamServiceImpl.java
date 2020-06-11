package com.health.item.service.impl;

import com.health.item.mapper.IExamMapper;
import com.health.item.pojo.Examination;
import com.health.item.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/3 21:30
 */
@Service
public class ExamServiceImpl implements IExamService {

    @Autowired
    private IExamMapper examMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addExam(List<Examination> exams) {
        for (Examination exam : exams) {
            int num = this.examMapper.insert(exam);
            if (num == 0) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * 删除表格数据
     * @param examedtimes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteExcelData(String[] examedtimes) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date[] date1 = new Date[examedtimes.length];
        Examination[] examinations = new Examination[examedtimes.length];
        for (int i = 0; i < examedtimes.length; i++) {
            examinations[i] = new Examination();
        }
        try {
            for (int i = 0; i < examedtimes.length; i++) {
                date1[i] = sf.parse(examedtimes[i]);
                examinations[i].setExamedtime(new java.sql.Date(date1[i].getTime()));
                Example example = new Example(Examination.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("examedtime", examinations[i].getExamedtime());
                int num = this.examMapper.deleteByExample(example);
                System.out.println(num);
                if (num == 0) {
                    return 0;
                }
            }
            return 1;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public Examination SelectIdAndExamedtime(Long id) {
        Example example = new Example(Examination.class);
        example.selectProperties("id", "examedtime")
                .and()
                .andEqualTo("id", id);
        return this.examMapper.selectOneByExample(example);
    }

    @Override
    public List<Examination> findByUid(String id, Long uid) {
        Example example = new Example(Examination.class);
        example.selectProperties(id, "examedtime")
                .and()
                .andEqualTo("userId", uid);
        return this.examMapper.selectByExample(example);
    }

    @Override
    public List<Examination> findAllByUid(Long uid) {
        Examination record = new Examination();
        record.setUserId(uid);
        return this.examMapper.select(record);
    }

    @Override
    public List<Examination> findAll() {
        return this.examMapper.selectAll();
    }
}

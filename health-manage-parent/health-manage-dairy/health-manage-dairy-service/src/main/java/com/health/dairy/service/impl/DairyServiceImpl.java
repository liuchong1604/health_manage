package com.health.dairy.service.impl;

import com.health.dairy.bo.DairyBo;
import com.health.dairy.client.DairyClient;
import com.health.dairy.mapper.IDairyMapper;
import com.health.dairy.pojo.Dairy;
import com.health.dairy.pojo.FoodDairy;
import com.health.dairy.service.IDairyService;
import com.health.food.pojo.Food;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 10:24
 */
@Service
public class DairyServiceImpl implements IDairyService {

    @Autowired
    private IDairyMapper dairyMapper;

    @Autowired
    private DairyClient dairyClient;
    @Override
    public List<Dairy> findByCreatedtimes(String[] dates) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date[] date1 = new Date[dates.length];
        Dairy[] dairies = new Dairy[dates.length];
        for(int i=0;i<dates.length;i++){
            dairies[i] = new Dairy();
        }
        try {
            for(int i=0;i<dates.length;i++) {
                //使用SimpleDateFormat的parse()方法生成Date
                date1[i] = sf.parse(dates[i]);
                dairies[i].setCreatedtime(new java.sql.Date(date1[i].getTime()));
            }
            List<Dairy> dairies1 = new ArrayList<>();
            for(int i=0;i<dates.length;i++){
                System.out.println(dairies[i].getCreatedtime());
                Example example = new Example(Dairy.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("createdtime",dairies[i].getCreatedtime());
                Dairy dairy = this.dairyMapper.selectOneByExample(example);
                System.out.println(dairy);
                dairies1.add(dairy);
            }
            return dairies1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑日志
     * @param dairyBo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDairy(DairyBo dairyBo) {
        // 先删除中间表这天数据
        System.out.println(new java.sql.Date(dairyBo.getDairy().getCreatedtime().getTime()));
        Dairy record = new Dairy();
        record.setCreatedtime(new java.sql.Date(dairyBo.getDairy().getCreatedtime().getTime()));
        Dairy dairy = this.dairyMapper.selectOne(record);
        this.dairyMapper.deleteFoodAndDairy(dairy.getId()); //  删除中间表
        Example example = new Example(Dairy.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",dairy.getId());
        dairyBo.getDairy().setCreatedtime(new java.sql.Date(dairyBo.getDairy().getCreatedtime().getTime()));
        Dairy dairy1 = new Dairy();
        BeanUtils.copyProperties(dairyBo.getDairy(),dairy1);
        this.dairyMapper.updateByExample(dairy1,example);
        insertFoodAndDairy(dairyBo,dairy.getId());
    }

    private void insertFoodAndDairy(DairyBo dairyBo,Long did){
        String[] names = new String[dairyBo.getFood().size()];
        for(int i=0;i<dairyBo.getFood().size();i++){
            names[i] = dairyBo.getFood().get(i).getFoodname();
            System.out.println(names[i]);
        }
        List<Food> foods = this.dairyClient.findByNames(names).getBody();
        foods.forEach((food) ->
                this.dairyMapper.insertDairyAndFood(did,food.getId())
        );
    }
    /**
     * 新增日志
     * @param dairyBo,uid
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDairy(DairyBo dairyBo) {
        //先新增dairy表
        dairyBo.getDairy().setCreatedtime(new java.sql.Date(dairyBo.getDairy().getCreatedtime().getTime()));
        Dairy dairy = new Dairy();
        BeanUtils.copyProperties(dairyBo.getDairy(),dairy);  // 对象属性复制,将一个对象的属性值赋值给另一个对象, 属性名需要相同
        this.dairyMapper.insertSelective(dairy);
        Dairy record = new Dairy();
        record.setCreatedtime(dairy.getCreatedtime());
        Dairy dairy1 = this.dairyMapper.selectOne(record);
        // 再新增中间表
        insertFoodAndDairy(dairyBo,dairy1.getId());
//        for(int i=0;i<foods.size();i++){
//            this.dairyMapper.insertDairyAndFood(dairyBo.getId(),foods.get(i).getId(),foods.get(i).getHeat());
//        }
    }

    @Override
    public Dairy findByCreatedtime(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
        try {
            Date date1 = sf.parse(date);
            Dairy dairy = new Dairy();
            dairy.setCreatedtime(new java.sql.Date(date1.getTime()));
            return this.dairyMapper.selectOne(dairy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Food> findFoods(Long did) {
        List<Food> foods = this.dairyMapper.findFoods(did);
        return foods;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(String createdtime) {
        Dairy record = new Dairy();
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
        Date date = null;
        try {
            date = sf.parse(createdtime);
            record.setCreatedtime(new java.sql.Date(date.getTime()));
            Long did = this.dairyMapper.selectOne(record).getId();
            this.dairyMapper.deleteFoodAndDairy(did);
            return this.dairyMapper.delete(record);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Dairy> findAllByUid(Long uid) {
        Dairy record = new Dairy();
        record.setUserId(uid);
        return this.dairyMapper.select(record);
    }
}

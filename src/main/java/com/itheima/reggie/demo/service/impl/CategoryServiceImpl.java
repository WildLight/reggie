package com.itheima.reggie.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.demo.common.CustomException;
import com.itheima.reggie.demo.entity.Category;
import com.itheima.reggie.demo.entity.Dish;
import com.itheima.reggie.demo.entity.Setmeal;
import com.itheima.reggie.demo.mapper.CategoryMapper;
import com.itheima.reggie.demo.service.CategoryService;
import com.itheima.reggie.demo.service.DishService;
import com.itheima.reggie.demo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id){
        //添加查询条件，根据分类id进行查询菜品数据
        QueryWrapper<Dish> qw = new QueryWrapper<>();
        qw.eq("category_id",id);
        int count = dishService.count(qw);
        //如果已经关联菜品，抛出一个业务异常
        if (count>0){
            throw new CustomException("当前分类关联了菜品,不能删除");
        }
        //添加条件查询,根据分类id进行查询菜品数据
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",id);
        int count1 = setmealService.count(queryWrapper);
        //如果已经关联套餐,抛出一个业务异常
        if (count1 > 0 ){
            throw new CustomException("当前菜品关联了套餐,不能删除");
        }
        super.removeById(id);
    }
}

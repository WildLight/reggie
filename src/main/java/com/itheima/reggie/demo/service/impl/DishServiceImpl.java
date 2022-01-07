package com.itheima.reggie.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.demo.entity.Category;
import com.itheima.reggie.demo.entity.Dish;
import com.itheima.reggie.demo.mapper.CategoryMapper;
import com.itheima.reggie.demo.mapper.DishMapper;
import com.itheima.reggie.demo.service.CategoryService;
import com.itheima.reggie.demo.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}

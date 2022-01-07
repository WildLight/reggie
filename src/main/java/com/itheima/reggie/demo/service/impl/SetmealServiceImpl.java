package com.itheima.reggie.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.demo.entity.Setmeal;
import com.itheima.reggie.demo.mapper.SetmealMapper;
import com.itheima.reggie.demo.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}

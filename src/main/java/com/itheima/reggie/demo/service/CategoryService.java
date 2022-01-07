package com.itheima.reggie.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.demo.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

package com.itheima.reggie.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.itheima.reggie.demo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author TangCheng
 * @date 2022/1/5 10:38
 * @description
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

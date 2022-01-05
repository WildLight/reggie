package com.itheima.reggie.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.demo.entity.Employee;
import com.itheima.reggie.demo.mapper.EmployeeMapper;
import com.itheima.reggie.demo.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author TangCheng
 * @date 2022/1/5 10:39
 * @description
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

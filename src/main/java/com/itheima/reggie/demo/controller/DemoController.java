package com.itheima.reggie.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.reggie.demo.entity.Employee;
import com.itheima.reggie.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TangCheng
 * @date 2022/1/5 10:35
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getInfos")
    public Object getInfos() {
        log.info("开始请求!");
        LambdaQueryWrapper<Employee> wrapper = Wrappers.<Employee>lambdaQuery().orderByDesc(Employee::getCreateTime);
        List<Employee> result = employeeService.list(wrapper);
        return result;
    }
}

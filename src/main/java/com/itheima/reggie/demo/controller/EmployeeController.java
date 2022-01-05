package com.itheima.reggie.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.reggie.demo.common.R;
import com.itheima.reggie.demo.entity.Employee;
import com.itheima.reggie.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author TangCheng
 * @date 2022/1/5 10:35
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //将页面提交的密码进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> wrapper=Wrappers.<Employee>lambdaQuery().eq(Employee::getUsername,employee.getUsername());
        Employee emp=employeeService.getOne(wrapper);
        //
        if(emp==null){
            return R.error("该用户不存在");
        }
        if(!emp.getPassword().equals(employee.getPassword())){
            return R.error("密码不正确");
        }
        if(emp.getStatus()!=1){
            return R.error("该用户不可用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        emp.setPassword(null);
        return R.success(emp);
    }
}

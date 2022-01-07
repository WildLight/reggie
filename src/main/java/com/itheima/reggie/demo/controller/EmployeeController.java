package com.itheima.reggie.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.demo.common.R;
import com.itheima.reggie.demo.entity.Employee;
import com.itheima.reggie.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
    //登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //将页面提交的密码进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> wrapper=Wrappers.<Employee>lambdaQuery().eq(Employee::getUsername,employee.getUsername());
        Employee emp=employeeService.getOne(wrapper);
        //判断用户是否存在
        if(emp==null){
            return R.error("该用户不存在");
        }
        //判断密码是否输入正确
        if(!emp.getPassword().equals(employee.getPassword())){
            return R.error("密码不正确");
        }
        //判断该用户是否被禁用
        if(emp.getStatus()!=1){
            return R.error("该用户不可用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        emp.setPassword(null);
        return R.success(emp);
    }
    //退出
    @PostMapping("/logout")
    public R<String> logOut(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    //添加员工
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request,@RequestBody Employee employee){
        log.info("员工的信息为{}",employee);
        //设置默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setStatus(1);
        //设置创建者信息
//        Long empId=(Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.save(employee);
        return R.success("添加成功");
    }
    //获取数据库员工数据
    @GetMapping("/page")
        public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
   //根据ID修改员工信息
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }
}

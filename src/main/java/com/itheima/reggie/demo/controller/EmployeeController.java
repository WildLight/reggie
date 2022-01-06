package com.itheima.reggie.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    @PostMapping("/logout")
    public R<String> logOut(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新员工，员工信息：{}",employee.toString());
        //设置初始密码,并对其进行MD5的加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置创建时间
        employee.setCreateTime(LocalDateTime.now());
        //设置修改时间
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前登录的用户
        Long eId = (Long) request.getSession().getAttribute("employee");
        //设置创建人id
        employee.setCreateUser(eId);
        employee.setUpdateUser(eId);
        //调用添加员工的方法
        log.info("新员工，员工信息：{}",employee.toString());
        employeeService.save(employee);
        return R.success("添加员工成功");
    }

    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String name) {
        //页面发送ajax请求，将分页查询参数(page、pageSize、name)提交到服务端
        Page pages = new Page(page, pageSize);
        //构造条件构造器
        QueryWrapper<Employee> queryWrapper = new QueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        //添加排序条件
        queryWrapper.orderByDesc("Update_time");
        //执行查询
        employeeService.page(pages, queryWrapper);
        return R.success(pages);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        //获取当前登录状态
        Long id = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("修改成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("修改员工信息");
        Employee byId = employeeService.getById(id);
        if (byId != null) {
            return R.success(byId);
        }
        return R.error("查无此信息");
    }
}

package com.gataway.controller;

import com.alibaba.fastjson.JSONObject;

import com.gataway.common.base.BaseResponse;
import com.gataway.entity.ManagerInfo;
import com.gataway.entity.ManagerToken;
import com.gataway.service.ManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    @Autowired
    private ManagerService managerService;

    @RequestMapping("/login")
        public JSONObject login(@RequestParam("username") String username,
                @RequestParam("password") String password) {
            JSONObject json = new JSONObject();
            json.put("result", false);
            json.put("msg", "账号或密码不正确");

            // 用户信息
            ManagerInfo managerInfo = managerService.getManagerInfo(username);
            // 账号不存在、密码错误
            if (managerInfo == null || !managerInfo.getPassword().equals(password)) {
                return json;
            }

            ManagerToken managerToken = managerService.saveToken(managerInfo.managerId);//传入管理员id
            json.put("token", managerToken.token);
            json.put("result", true);
            json.put("msg", "登陆成功");

            return json;
    }

    /**
     * 需要是超级管理员的token才能查看,
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public BaseResponse requireRole() {
        SecurityManager securityManager=new SecurityManager();
        return new BaseResponse(true, "You are visiting require_role", null);
    }
    @RequiresRoles("admin")
    @GetMapping("/test/pzhu")
    public  BaseResponse test(){
        SecurityManager securityManager=new SecurityManager();
        return  new BaseResponse(true,"测试成功",null);
    }


    /**
     * 需要有update权限才能访问
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"update"})
    public BaseResponse requirePermission() {
        return new BaseResponse(true, "You are visiting permission require update", null);
    }

}

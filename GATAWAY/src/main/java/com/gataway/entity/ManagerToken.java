package com.gataway.entity;

import lombok.Data;


@Data
public class ManagerToken {
    //管理员ID
    public Integer managerId;
    //token
    public String token;
    //过期时间
    public String expireTime;
    //更新时间
    public String updateTime;


}

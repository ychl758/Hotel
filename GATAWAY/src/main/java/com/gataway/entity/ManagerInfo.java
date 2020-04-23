package com.gataway.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
//不必序列化
@Data
public class ManagerInfo {
    public Integer managerId;
    public String managerName;
    public String nickName;
    public String password;
    public Integer managerLevelId;
    public String managerLevelName;


}

package com.gataway.service;


import com.gataway.common.base.AbstractService;
import com.gataway.common.oauth2.TokenGenerator;
import com.gataway.entity.ManagerInfo;
import com.gataway.entity.ManagerToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;



@Service
public class ManagerService extends AbstractService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12 * 1000;

    public ManagerInfo getManagerInfo(String managerName) {
        String sql = "select a.managerName, a.managerLevelId,a.managerId, a.password"
                + " from manager a where a.managerName=?";
        ManagerInfo manager = jdbcDao.queryForObject(sql, new Object[] { managerName }, ManagerInfo.class);
        return manager;
    }

    public ManagerToken saveToken(Integer managerId) {//这里接收了管理员的id 自增
        ManagerToken managerToken = new ManagerToken();
        managerToken.managerId = managerId;

        //生成一个token
        managerToken.token = TokenGenerator.generateValue();
        //过期时间  过期时间为生成token的12小时过期
        Date expireTime = new Date(System.currentTimeMillis() + EXPIRE);

        // 更新时间/过期时间
        SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");//时间格式
        Date systemDate = new Date();
        managerToken.updateTime = sm.format(systemDate);
        managerToken.expireTime = sm.format(expireTime);

        String sql = "insert into managertoken (managerId, token, updateTime, expireTime) values (?,?,?,?)"
                + " ON DUPLICATE KEY UPDATE token=?, updateTime=?, expireTime=?";
        jdbcDao.update(sql, new Object[]{managerToken.managerId, managerToken.token, managerToken.updateTime,
                managerToken.expireTime, managerToken.token, managerToken.updateTime, managerToken.expireTime});

        return managerToken;
    }

    @Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, readOnly = true, rollbackFor = Exception.class)
    public ManagerInfo getManagerInfo(Integer managerId) {
        if (managerId == null) {
            return null;
        }

        String sql = "select a.managerId, a.managerName, a.managerLevelId from manager a " +
                "where a.managerId=?";
        ManagerInfo manager = jdbcDao.queryForObject(sql, new Object[]{managerId}, ManagerInfo.class);

        return manager;
    }

    public ManagerToken queryByToken(String token) {
        if (token == null || "".equals(token)) {
            return null;
        }

        String sql = "select managerid managerId, token, expireTime, updateTime from managertoken where token=?";
        ManagerToken managerToken = jdbcDao.queryForObject(sql, new Object[]{token}, ManagerToken.class);

        return managerToken;
    }

}

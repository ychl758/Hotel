package com.gataway.apiconfig;

import com.gataway.service.ManagerService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 拦截器
 */
@Component
public class ApiFilter extends ZuulFilter {

    @Autowired
    ManagerService managerService;

    @Override
    public String filterType() {
        return "pre";
        //filterType 为过滤类型，
        // 可选值有 pre（路由之前）、
        // routing（路由之时）
        // 、post（路由之后）、
        // error（发生错误时调用）
    }

    @Override
    public int filterOrder() {
        return 0;
        // 为过滤的顺序，如果有多个过滤器，则数字越小越先执行
    }

    @Override
    public boolean shouldFilter() {
       return  true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String url=request.getRequestURL().toString();
        System.out.println(url);
        Subject subject=SecurityUtils.getSubject();
        String [] urls=url.split("/");
        System.out.println(urls[4]);
        if(subject.hasRole("admin")&&urls[4].trim().equals("yggl")){
            return context;
        }else if(subject.hasRole("test")&&urls[4].trim().equals("kckf")){
            return  context;
        }else {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                context.getResponse().getWriter().write("Not enough permissions!");
            } catch (Exception e) {
            }
        }
        String token = request.getParameter("token");
            if (token == null) {
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(401);
                try {
                    context.getResponse().getWriter().write("zuullanjie");
                } catch (Exception e) {
                }
        }
        return context;
    }

}

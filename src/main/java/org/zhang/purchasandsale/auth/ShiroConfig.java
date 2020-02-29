package org.zhang.purchasandsale.auth;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;


/**
 * @author: zhanglw
 * @Date: 2020/2/29 15:32
 * @Description:
 */
@Configurable
public class ShiroConfig {
    /**
     * 配置Shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。（类似于SpringMVC中的DispatcherServlet控制器）
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的realm交给SecurityManager管理
        securityManager.setRealm(new CustomRealm());
        return securityManager;
    }
}

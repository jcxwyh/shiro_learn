package pro.onlyou.apache.shiro.boot.config;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.onlyou.apache.shiro.boot.security.AuthRealm;
import pro.onlyou.apache.shiro.boot.security.MyCredentialMatcher;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    /**
     *
     * @return
     */
    @Bean("credentialMatcher")
    public MyCredentialMatcher credentialMatcher(){
        return new MyCredentialMatcher();
    }

    /**
     *
     * @param credentialMatcher
     * @return
     */
    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher") MyCredentialMatcher credentialMatcher){
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(credentialMatcher);
        return authRealm;
    }

    /**
     *
     * @param authRealm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
        DefaultWebSecurityManager securityManager =
                new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(authRealm);
        // 设置一个基于内存的缓存
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());

        return securityManager;
    }

    /**
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean =
                new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/welcome");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorize");

        // 需要保证存值顺序，所以使用LinkedHashMap
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 代表/welcome的路径
        filterChainDefinitionMap.put("/welcome","authc");
        // 代表/login的路径不需要拦截
        filterChainDefinitionMap.put("/login","anon");
        // 配置Druid数据库监控不需要拦截
        filterChainDefinitionMap.put("/druid/**","anon");
        // 代表/hello的路径只有角色为admin的用户能访问 -----> RolesAuthorizationFilter
        filterChainDefinitionMap.put("/hello","roles[ADMIN]");
        // 代表/update这个路径只有拥有update权限的用户能访问　－－－－> PermissionsAuthorizationFilter
        filterChainDefinitionMap.put("/update","perms[update]");
        // 代表/welcome的路径
        filterChainDefinitionMap.put("/**","user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**--------------------------------------------------------/
     *                                                         /
     *              下面的配置spring整合shiro                      /
     *                                                         /
     ---------------------------------------------------------*/

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor =
                new AuthorizationAttributeSourceAdvisor();

        advisor.setSecurityManager(securityManager);

        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator =
                new DefaultAdvisorAutoProxyCreator();

        creator.setProxyTargetClass(true);

        return creator;
    }
}

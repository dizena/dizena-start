package org.dizena.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.dizena.common.realm.JwtFilter;
import org.dizena.common.realm.ShiroJwtRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());

        Map<String, String> filterRuleMap = new HashMap<>();
        // 文档方面
        filterRuleMap.put("/doc.html", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");

        // new
        filterRuleMap.put("/", "anon");
        filterRuleMap.put("/error", "anon");
        filterRuleMap.put("/guest/**", "anon");
        filterRuleMap.put("/**", "jwt,authc");
        //end
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        //factoryBean.setLoginUrl("/guest/first/none");
        //factoryBean.setUnauthorizedUrl("/guest/first/none");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public SecurityManager securityManager(@Autowired ShiroJwtRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     @Bean
     @DependsOn({"lifecycleBeanPostProcessor"}) public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
     DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
     advisorAutoProxyCreator.setUsePrefix(true);
     return advisorAutoProxyCreator;
     }

     @Bean public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager) {
     AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
     authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
     return authorizationAttributeSourceAdvisor;
     }

     @Bean public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
     return new LifecycleBeanPostProcessor();
     }
     */


}

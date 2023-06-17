package com.xyai.shenniuban.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author lcz
 * @apiNote shiro 配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 加密方式
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        // 散列凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置哈希算法名称,这里使用MD5算法
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 设置哈希迭代，这里迭代2次，相当于 md5(md5(""))
        credentialsMatcher.setHashIterations(2);
        // 设置存储的凭据16进制编码,需要和生成密码时的一样，默认是 Base64
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * 自定义Realm
     * @param cacheManager
     * @return
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm(EhCacheManager cacheManager) {
        ShiroRealm realm = new ShiroRealm();
        realm.setCacheManager(cacheManager);
        return realm;
    }

    /**
     * 缓存管理器
     * @return
     */
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    /**
     * 安全管理器
     * @param shiroRealm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm) {
        // 实例化会话管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置缓存管理器
        securityManager.setCacheManager(ehCacheManager());

        /**
         * 关闭shiro自带的session
         * 详情见文档: http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);

        securityManager.setSubjectDAO(subjectDAO);

        // 设置自定义Realm
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * 过滤工厂
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt", new JWTFilter());
        // 添加过滤器
        factoryBean.setFilters(filters);
        // 配置过滤链
        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterChainDefinitionManager.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
        return factoryBean;
    }

    /**
     * 自动代理配置
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager);
        return aASA;
    }
}

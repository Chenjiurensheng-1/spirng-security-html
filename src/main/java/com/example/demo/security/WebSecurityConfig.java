package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * SpringSecurity的配置
 * @author 程就人生
 * @date 2019年5月26日
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService myCustomUserService;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.cors().and().csrf().disable();
    	
        http
            //使用form表单post方式进行登录
            .formLogin()
            //登录页面为自定义的登录页面
            .loginPage("/login")
            //设置登录成功跳转页面
            .successForwardUrl("/index").failureUrl("/login?error=true")
            .permitAll()
            .and()
            //允许不登陆就可以访问的方法，多个用逗号分隔
            .authorizeRequests().antMatchers("/test").permitAll()
            //其他的需要授权后访问
            .anyRequest().authenticated();
        
        
	        //session管理,失效后跳转  
	        http.sessionManagement().invalidSessionUrl("/login"); 
	        //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线 
	        //http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy()); 
	        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录 
	        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true); 
	        //退出时情况cookies
	        http.logout().deleteCookies("JESSIONID"); 
	        //解决中文乱码问题 
	        CharacterEncodingFilter filter = new CharacterEncodingFilter(); 
	        filter.setEncoding("UTF-8"); filter.setForceEncoding(true); 
	        //
	        http.addFilterBefore(filter,CsrfFilter.class); 
    }
    
//	@Bean
//	public SessionInformationExpiredStrategy expiredSessionStrategy() {
//		return new ExpiredSessionStrategy();
//	}
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        //返回错误信息提示，而不是Bad Credential
        bean.setHideUserNotFoundExceptions(true);
        //覆盖UserDetailsService类
        bean.setUserDetailsService(myCustomUserService);
        //覆盖默认的密码验证类
        bean.setPasswordEncoder(myPasswordEncoder); 
        return bean;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(this.daoAuthenticationProvider());
    }
}
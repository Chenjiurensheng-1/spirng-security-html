package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录专用类,用户登陆时，通过这里查询数据库
 * 自定义类，实现了UserDetailsService接口，用户登录时调用的第一类
 * @author 程就人生
 * @date 2019年5月26日
 */
@Component
public class MyCustomUserService implements UserDetailsService {

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String username){
    	//用户名不为空验证
    	if(StringUtils.isEmpty(username)){
    		throw new RuntimeException("用户名不能为空~！");
    	}
    	//用户名不存在验证
    	if(!username.equals("admin")){
    		throw new RuntimeException(String.format("账号【%s】不存在！", username));
    	}
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
    	MyUserDetails myUserDetail = new MyUserDetails();
		myUserDetail.setUsername(username);
		//假设后台取出来的密码为123456
		myUserDetail.setPassword("123456");
		List<String> userRoles = new ArrayList<String>();
		userRoles.add("ROLE_ADMINISTRATOR");     
		//权限设置
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();     
        for (String roleName : userRoles) {
        	//将角色也添加到权限之中
        	authorities.add(new SimpleGrantedAuthority(roleName)); 
//            authorities.add(new SimpleGrantedAuthority("appLogs:read"));   
//            authorities.add(new SimpleGrantedAuthority(String.format("%s:%s", "appUsers", "read")));
//            authorities.add(new SimpleGrantedAuthority(String.format("%s:%s", "industry", "read")));
        	
        }    
        //添加每个角色对应的菜单权限code
        authorities.add(new SimpleGrantedAuthority("index:read"));   
        authorities.add(new SimpleGrantedAuthority(String.format("%s:%s", "index", "write")));
        
		myUserDetail.setAuthorities(authorities);
		
        return myUserDetail;
    }
}
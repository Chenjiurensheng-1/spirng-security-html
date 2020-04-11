package com.example.demo.security;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * https://blog.csdn.net/cloume/article/details/83790111
 * https://www.cnblogs.com/fenglan/p/5913463.html
 * @author FengJuan
 * @date 2019年8月13日
 * @Description 
 *
 */
@Configuration
public class MyPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		boolean accessable = false;
		//匿名访问时，无权限
		if(authentication.getPrincipal().toString().compareToIgnoreCase("anonymousUser") != 0){
			//中间用冒号分隔
			String privilege = targetDomainObject + ":" + permission;
			for(GrantedAuthority authority : authentication.getAuthorities()){
				if(privilege.equalsIgnoreCase(authority.getAuthority())){
					accessable = true;
					break;
				}
			}
			
			return accessable;
		}

		return accessable;
	}

	/**
	 * 总是认为有权限返回true,否则返回false
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		return false;
	}
}

package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
 * @author 程就人生
 * @date 2019年6月8日
 */
@RestController
public class IndexController {

	
	
	@RequestMapping("/test")
	public Object test(){
		return "test";
	}
	
	/**
	 * 无权限控制，可以直接访问
	 * @return
	 *
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		return new ModelAndView("/index");
	}
	
	/**
	 * 使用@PreAuthorize对方法进行权限过滤
	 * @return
	 *
	 */
	@RequestMapping("/index1")
	@PreAuthorize("hasPermission('index', 'read') or hasRole('ROLE_ADMINISTRATOR')")
	public ModelAndView index1(){
		return new ModelAndView("/index");
	}
	/**
	 * 只有拥有index:read2的权限才可以访问
	 * @return
	 *
	 */
	@RequestMapping("/index2")
	@PreAuthorize("hasPermission('index', 'read2')")
	public ModelAndView index2(){
		return new ModelAndView("/index");
	}
	
	/**
	 * 自定义登录页面
	 * @param error 错误信息显示标识
	 * @return
	 *
	 */
	@GetMapping("/login")
	public ModelAndView login(String error){
		 ModelAndView modelAndView = new ModelAndView("/login");
		 modelAndView.addObject("error", error);
		return modelAndView;
	}	
}

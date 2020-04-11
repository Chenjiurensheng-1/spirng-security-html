package com.example.demo.security;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Spring Security提示信息配置文件
 * @author 程就人生
 * @date 2020年4月9日
 */
@Configuration
public class MySecurityMessages {

	/**
     * 自定义错误信息
     * @return
     */
    @Bean
	public MessageSource messageSource() {
        Locale.setDefault(Locale.CHINA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //中文提示信息配置文件
        messageSource.addBasenames("classpath:messages_zh_CN"); 
        return messageSource;
    }    
}

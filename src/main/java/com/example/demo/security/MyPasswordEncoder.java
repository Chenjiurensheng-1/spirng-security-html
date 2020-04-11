package com.example.demo.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义的密码加密方法，实现了PasswordEncoder接口
 * @author 程就人生
 * @date 2019年5月26日
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {    	
        //加密方法可以根据自己的需要修改
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
    	//密码比对
        return encode(charSequence).equals(s);
    }
}
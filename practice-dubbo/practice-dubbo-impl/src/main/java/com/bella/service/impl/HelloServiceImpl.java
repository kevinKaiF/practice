package com.bella.service.impl;

import com.bella.service.HelloService;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/22
 */
public class HelloServiceImpl implements HelloService, InitializingBean {
    private String name = "3343";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        name = "333";
    }

}

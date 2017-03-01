package cn.bella.service.impl;

import cn.bella.service.HelloService;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String getFullName(String name) {
        return "full " + name;
    }
}

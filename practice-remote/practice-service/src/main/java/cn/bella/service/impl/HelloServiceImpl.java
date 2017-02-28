package cn.bella.service.impl;


import cn.bella.service.HelloService;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/21
 */
public class HelloServiceImpl implements HelloService {
    public void sayHi(String name) {
        System.out.println("hi, " + name);
    }
}

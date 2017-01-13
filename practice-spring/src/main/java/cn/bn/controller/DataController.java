package cn.bn.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-29 PM03:52
 */
@Controller
public class DataController implements InitializingBean {
    public void afterPropertiesSet() throws Exception {
        System.out.println("init " + this.getClass().getName());
    }
}

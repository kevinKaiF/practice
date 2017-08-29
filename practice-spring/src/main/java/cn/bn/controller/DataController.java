package cn.bn.controller;

import cn.bn.service.TestBeanService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-29 PM03:52
 */
@Controller
public class DataController extends AbstractAction implements InitializingBean {
    public void afterPropertiesSet() throws Exception {
        System.out.println("init " + this.getClass().getName());
    }

    @Autowired
    public void setName(TestBeanService name) {
        this.name = name;
    }

    @RequestMapping("/test")
    public void testRequestMapping() {

    }

}

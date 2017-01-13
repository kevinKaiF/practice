package cn.bid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/15
 */
@Controller
public class IndexAction {

    @RequestMapping("index.do")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("hello");
        modelAndView.addObject("hello", "hello world");
        return modelAndView;
    }

    @InitBinder
    public void log() {
        System.out.println("executing init binder");
    }
}

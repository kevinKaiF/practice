package cn.bn.entity;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/31
 */
public class AbstractBean {
    @Value("${name}")
    private String name;

    public String getName() {
        return name;
    }

    private Car car;

    public void setCar(Car car) {
        this.car = car;
    }
}

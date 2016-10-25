package cn.bn.factory;

import cn.bn.entity.Car;
import org.springframework.beans.factory.FactoryBean;

/**
 * @description:
 * @author: <a href="jiangsai@ebnew.com">jiangsai </a>
 * @version: 1.0
 * @changelog: 变更日志记录
 * author                   date               comment
 * jiangsai@ebnew.com      2016/10/24             创建类
 */
public class CarFactory implements FactoryBean<Car> {
    private String carName;
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setName(carName);
        return car;
    }

    public Class<?> getObjectType() {
        return Car.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}

package cn.bn.factory;

import cn.bn.entity.Car;
import org.springframework.beans.factory.FactoryBean;

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

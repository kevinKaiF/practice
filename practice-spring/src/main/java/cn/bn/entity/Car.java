package cn.bn.entity;

/**
 * @description:
 * @author: <a href="jiangsai@ebnew.com">jiangsai </a>
 * @version: 1.0
 * @changelog: 变更日志记录
 * author                   date               comment
 * jiangsai@ebnew.com      2016/10/24             创建类
 */
public class Car {
    private String name;
    private double weight;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", color='" + color + '\'' +
                '}';
    }
}

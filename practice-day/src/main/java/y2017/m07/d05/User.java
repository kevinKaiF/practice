package y2017.m07.d05;

import java.io.Serializable;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/11
 */
public class User implements Serializable{
    private static final long serialVersionUID = -5697165961897387867L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

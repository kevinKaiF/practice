package cn.bella.entity;

import java.io.Serializable;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class RequestEntity implements Serializable {
    private Class invokeClass;
    private String invokeMethod;
    private Object[] params;

    public RequestEntity(Class invokeClass, String invokeMethod, Object[] params) {
        this.invokeClass = invokeClass;
        this.invokeMethod = invokeMethod;
        this.params = params;
    }

    public Class getInvokeClass() {
        return invokeClass;
    }

    public void setInvokeClass(Class invokeClass) {
        this.invokeClass = invokeClass;
    }

    public String getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(String invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}

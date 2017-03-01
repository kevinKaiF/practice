package cn.bella.entity;

import java.io.Serializable;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class ResponseEntity implements Serializable{
    private Object result;
    private boolean success;
    private String errorMsg;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

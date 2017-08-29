package y2017.m04.d28;


import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public class ListenableFutureCallbackRegistry<T> {

    private Queue<SuccessCallback<? super T>> successCallbacks = new LinkedList<>();

    private Queue<FailureCallback> failureCallbacks = new LinkedList<>();

    private Object mutex = new Object();

    private State state = State.NEW;

    private Object result;

    private enum State {NEW, SUCCESS, FAILURE}

    public void addCallback(ListenableFutureCallback<T> callback) {
        Assert.notNull(callback, "'callback' must not be null");
        // state为NEW 表示尚未执行完毕，可以添加需要的回调函数，
        // state为SUCCESS,FAILURE表示已执行完毕，触发回调函数
        synchronized (mutex) {
            switch (state) {
                case NEW:
                    this.successCallbacks.add(callback);
                    this.failureCallbacks.add(callback);
                    break;
                case SUCCESS:
                    callback.onSuccess((T) result);
                    break;
                case FAILURE:
                    callback.onFailure((Throwable) result);
                    break;
            }
        }
    }

    public void addSuccessCallback(SuccessCallback<? super T> callback) {
        Assert.isNull(callback, "'callback' must not be null");
        synchronized (mutex) {
            switch (state) {
                case NEW:
                    successCallbacks.add(callback);
                    break;
                case SUCCESS:
                    callback.onSuccess((T) result);
            }
        }
    }

    public void addFailureCallback(FailureCallback callback) {
        synchronized (mutex) {
            switch (state) {
                case NEW:
                    failureCallbacks.add(callback);
                    break;
                case FAILURE:
                    callback.onFailure((Throwable) result);
            }
        }
    }

    public void onSuccess(T result) {
        synchronized (mutex) {
            this.result = result;
            this.state = State.SUCCESS;
            while (!successCallbacks.isEmpty()) {
                successCallbacks.poll().onSuccess(result);
            }
        }
    }

    public void onFailure(Throwable ex) {
        synchronized (mutex) {
            this.result = ex;
            this.state = State.FAILURE;
            while (!failureCallbacks.isEmpty()) {
                failureCallbacks.poll().onFailure(ex);
            }
        }
    }
}

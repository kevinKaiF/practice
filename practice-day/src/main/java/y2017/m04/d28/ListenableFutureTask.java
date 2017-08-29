package y2017.m04.d28;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public class ListenableFutureTask<T> extends FutureTask<T> implements ListenableFuture<T> {

    private ListenableFutureCallbackRegistry callbackRegistry = new ListenableFutureCallbackRegistry();

    public ListenableFutureTask(Callable<T> callable) {
        super(callable);
    }

    public ListenableFutureTask(Runnable runnable, T result) {
        super(runnable, result);
    }

    @Override
    public void addCallback(ListenableFutureCallback<? super T> callback) {
        callbackRegistry.addCallback(callback);
    }

    @Override
    public void addCallback(SuccessCallback<? super T> successCallback, FailureCallback failureCallback) {
        callbackRegistry.addSuccessCallback(successCallback);
        callbackRegistry.addFailureCallback(failureCallback);
    }

    @Override
    protected void done() {
        Throwable cause;
        try {
            T result = get();
            this.callbackRegistry.onSuccess(result);
            return;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return;
        } catch (ExecutionException ex) {
            cause = ex.getCause();
            if (cause == null) {
                cause = ex;
            }
        } catch (Throwable ex) {
            cause = ex;
        }
        this.callbackRegistry.onFailure(cause);
    }
}

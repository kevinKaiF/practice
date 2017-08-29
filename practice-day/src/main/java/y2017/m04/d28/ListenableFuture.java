package y2017.m04.d28;

import java.util.concurrent.Future;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public interface ListenableFuture<T> extends Future<T> {

    void addCallback(ListenableFutureCallback<? super T> callback);

    void addCallback(SuccessCallback<? super T> successCallback, FailureCallback failureCallback);
}

package y2017.m04.d28;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public abstract class ListenableFutureAdapter<T, S> extends FutureAdapter<T, S> implements ListenableFuture<T> {

    public ListenableFutureAdapter(Future<S> adaptee) {
        super(adaptee);
    }

    @Override
    public void addCallback(ListenableFutureCallback<? super T> callback) {
        addCallback(callback, callback);
    }

    @Override
    public void addCallback(final SuccessCallback<? super T> successCallback, final FailureCallback failureCallback) {
        // 先获取结果，再进行回调
        final ListenableFuture<S> adaptee = (ListenableFuture<S>) getAdaptee();
        adaptee.addCallback(new ListenableFutureCallback<S>() {
            @Override
            public void onSuccess(S result) {
                try {
                    successCallback.onSuccess(adpatInternal(adaptee.get()));
                } catch (ExecutionException ex) {
                    Throwable cause = ex.getCause();
                    onFailure(cause != null ? cause : ex);
                } catch (Throwable ex) {
                    onFailure(ex);
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                failureCallback.onFailure(ex);
            }
        });
    }


}

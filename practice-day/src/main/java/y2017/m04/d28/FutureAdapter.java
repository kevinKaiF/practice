package y2017.m04.d28;


import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public abstract class FutureAdapter<T, S> implements Future<T> {

    private Future<S> adaptee;

    private State state = State.NEW;

    private Object result;

    private Object mutex = new Object();

    public FutureAdapter(Future<S> adaptee) {
        Assert.notNull(adaptee, "'delegate' must not be null");
        this.adaptee = adaptee;
    }

    protected Future<S> getAdaptee() {
        return adaptee;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return adaptee.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return adaptee.isCancelled();
    }

    @Override
    public boolean isDone() {
        return adaptee.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return adpatInternal(adaptee.get());
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return adpatInternal(adaptee.get(timeout, unit));
    }

    protected T adpatInternal(S adaptResult) throws ExecutionException {
        synchronized (mutex) {
            switch (state) {
                case SUCCESS:
                    return (T) result;
                case FAILURE:
                    throw (ExecutionException) result;
                case NEW:
                    try {
                        T adapted = adpat(adaptResult);
                        this.result = adapted;
                        this.state = State.SUCCESS;
                        return adapted;
                    } catch (ExecutionException ex) {
                        this.result = ex;
                        this.state = State.FAILURE;
                        throw ex;
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    protected abstract T adpat(S adaptResult) throws ExecutionException;


    enum State {NEW, SUCCESS, FAILURE}
}

package ssobocik.fp.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ssobocik.fp.exceptions.exceptionHandling.FailureCallback;

/**
 * Base for all asyncCallbacks with default handling of failure
 *
 * @author szymon.sobocik
 */
public abstract class FAsyncCallback<T> implements AsyncCallback<T> {
    @Override
    public void onFailure(Throwable throwable) {
        //todo make "Please wait"
        onFailureFP(throwable);
    }

    public void onFailureFP(Throwable throwable) {
        FailureCallback.onFailure(throwable);
    }

    @Override
    public void onSuccess(T t) {
        //todo make "Please wait"
        onSuccessFP(t);
    }

    public abstract void onSuccessFP(T t);

}

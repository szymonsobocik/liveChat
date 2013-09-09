package ssobocik.fp.exceptions.exceptionHandling;

import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;

/**
 * Simple handles majority of exceptions thrown from server side to client side
 *
 * @author szymon.sobocik
 */
public class FailureCallback {

    public static void onFailure(Throwable e) {
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        Throwable throwable = unwrap(e);
        Window.alert(throwable.getMessage());
    }

    /**
     * Unravel GWT’s UmbrellaException
     */
    private static Throwable unwrap(Throwable e) {
        if (e instanceof UmbrellaException) {
            UmbrellaException ue = (UmbrellaException) e;
            if (ue.getCauses().size() == 1) {
                return unwrap(ue.getCauses().iterator().next());
            }
        }
        return e;
    }
}

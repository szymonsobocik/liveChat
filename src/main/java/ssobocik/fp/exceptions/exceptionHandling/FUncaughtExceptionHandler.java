package ssobocik.fp.exceptions.exceptionHandling;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Handles all uncought exceptions that ends up on client side
 *
 * @author szymon.sobocik
 */
public class FUncaughtExceptionHandler implements GWT.UncaughtExceptionHandler {

    static final Logger logger = Logger.getLogger(FUncaughtExceptionHandler.class.getName());

    @Override
    public void onUncaughtException(Throwable e) {
        FailureCallback.onFailure(e);
        logger.log(Level.WARNING, e.getMessage(), e);
    }
}

package ssobocik.fp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.RootPanel;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.view.FResource;
import ssobocik.fp.exceptions.exceptionHandling.FUncaughtExceptionHandler;
import ssobocik.fp.client.view.desktop.Desktop;

/**
 * Main class of a module, whole app start's here
 *
 * @author szymon.sobocik
 */
public class FPAppEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        configureExceptionHandling();
        FResource.INSTANCE.cssMain().ensureInjected();

        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                onModuleLoadActual();
            }
        });
    }

    /**
     * Invoked from schedule deferred, becouse of exception handling configuration,
     * which actually takes place afeter onModuleLoad method finishes
     */
    private void onModuleLoadActual() {
        /**
         * Initialize events
         */
        InitEvents.getInstance();


        Desktop desktop = Desktop.getInstance();
        RootPanel.get("content").add(desktop);
    }

    private void configureExceptionHandling() {
        GWT.setUncaughtExceptionHandler(new FUncaughtExceptionHandler());


    }
}

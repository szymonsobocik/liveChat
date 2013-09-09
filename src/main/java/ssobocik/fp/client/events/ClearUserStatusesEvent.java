package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Informs that all user statuses, should be cleared, becouse new check is runnin
 * @author szymon.sobocik
 */
public class ClearUserStatusesEvent extends GwtEvent<ClearUserStatusesEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    public interface Handler extends EventHandler {
        public void handle(ClearUserStatusesEvent event);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.handle(this);
    }
}


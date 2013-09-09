package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that calls form for adding new user
 *
 * @author szymon.sobocik
 */
public class ShowNewUserFormEvent extends GwtEvent<ShowNewUserFormEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    public interface Handler extends EventHandler {
        public void handle(ShowNewUserFormEvent event);
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

package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author szymon.sobocik
 */
public class ShowSignInFormEvent extends GwtEvent<ShowSignInFormEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    public interface Handler extends EventHandler {
        public void handle(ShowSignInFormEvent event);
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

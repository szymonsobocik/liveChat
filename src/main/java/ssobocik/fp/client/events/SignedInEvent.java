package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import ssobocik.fp.dto.UserDTO;

/**
 * Fired when user signed in
 *
 * @author szymon.sobocik
 */
public class SignedInEvent extends GwtEvent<SignedInEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    public interface Handler extends EventHandler {
        public void handle(SignedInEvent event);
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

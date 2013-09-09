package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import ssobocik.fp.client.atmosphere.messages.UserStatusMessage;
import ssobocik.fp.dto.UserStatusDTO;

/**
 * @author szymon.sobocik
 */
public class RefreshUserStatusEvent extends GwtEvent<RefreshUserStatusEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    private UserStatusDTO userStatusDTO;

    public RefreshUserStatusEvent(UserStatusDTO userStatusDTO) {
        this.userStatusDTO = userStatusDTO;
    }

    public interface Handler extends EventHandler {
        public void handle(RefreshUserStatusEvent event);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.handle(this);
    }

    public UserStatusDTO getUserStatusDTO() {
        return userStatusDTO;
    }
}

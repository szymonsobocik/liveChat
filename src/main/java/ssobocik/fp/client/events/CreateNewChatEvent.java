package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import ssobocik.fp.dto.UserDTO;

/**
 * Request creation of new chat
 *
 * @author szymon.sobocik
 */
public class CreateNewChatEvent extends GwtEvent<CreateNewChatEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    private UserDTO userToTalkTo;

    public CreateNewChatEvent(UserDTO userToTalkTo) {
        this.userToTalkTo = userToTalkTo;
    }

    public interface Handler extends EventHandler {
        public void handle(CreateNewChatEvent event);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.handle(this);
    }

    public UserDTO getUserToTalkTo() {
        return userToTalkTo;
    }
}

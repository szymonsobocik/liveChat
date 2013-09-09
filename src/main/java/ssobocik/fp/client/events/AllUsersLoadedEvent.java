package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import ssobocik.fp.dto.UserDTO;

import java.util.List;

/**
 * Fired when users for panel in main screen are loaded
 *
 * @author szymon.sobocik
 */
public class AllUsersLoadedEvent extends GwtEvent<AllUsersLoadedEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    public interface Handler extends EventHandler {
        public void handle(AllUsersLoadedEvent event);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.handle(this);
    }

    private List<UserDTO> allUsers;

    public AllUsersLoadedEvent(List<UserDTO> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserDTO> getAllUsers() {
        return allUsers;
    }
}

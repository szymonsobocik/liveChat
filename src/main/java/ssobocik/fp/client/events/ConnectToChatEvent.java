package ssobocik.fp.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import ssobocik.fp.client.atmosphere.messages.InviteUserMessage;

/**
 * Triggers users browser to open new chat, that he was invited to
 *
 * @author szymon.sobocik
 */
public class ConnectToChatEvent extends GwtEvent<ConnectToChatEvent.Handler> {

    public static final Type<Handler> TYPE = new Type<Handler>();

    private InviteUserMessage inviteUserMessage;

    public ConnectToChatEvent(InviteUserMessage inviteUserMessage) {
        this.inviteUserMessage = inviteUserMessage;
    }

    public interface Handler extends EventHandler {
        public void handle(ConnectToChatEvent event);
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.handle(this);
    }

    public InviteUserMessage getInviteUserMessage() {
        return inviteUserMessage;
    }

    public void setInviteUserMessage(InviteUserMessage inviteUserMessage) {
        this.inviteUserMessage = inviteUserMessage;
    }
}
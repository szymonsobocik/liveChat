package ssobocik.fp.client.atmosphere;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.atmosphere.messages.ChatMessage;
import ssobocik.fp.client.view.atmosphere.ChatRoomBase;

import java.util.logging.Logger;

/**
 * @author szymon.sobocik
 */
public abstract class BaseAtmosphere {

    static final Logger logger = Logger.getLogger(BaseAtmosphere.class.getName());

    protected AtmosphereClient client;
    private BaseAtmosphereListener atmosphereListener;
    private AtmosphereGWTSerializer serializer = GWT.create(ChatMessageSerializer.class);

    protected String room;
    protected ChatRoomBase chatRoom;
    protected boolean connected = false;

    public void initAtmosphere() {
        stopAtmosphere();
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                client = new AtmosphereClient(getUrl(), serializer, getAtmosphereListener());
                atmosphereListener.setClient(client);
                client.start();
            }
        });
    }

    public void stopAtmosphere(){
        logger.info("stopping atmosphere " + room + " client: " + client);
        if (client != null) {
            client.stop();
            client = null;
        }
    }

    private String getUrl() {
        return GWT.getModuleBaseURL() + "gwtComet/" + room;
    }

    private BaseAtmosphereListener getAtmosphereListener() {
        if (atmosphereListener == null) {
            atmosphereListener = createAtmosphereListener();
        }
        return atmosphereListener;
    }


    public void sendMessage(String message) {
        logger.info("send message: " + message);
        if (canBroadcast()) {
            ChatMessage chatMessage = createChatMessage(message);
            client.broadcast(chatMessage);
        }
    }

    protected ChatMessage createChatMessage(String message) {
        return new ChatMessage(FClientUtils.getInstance().getSignedInUser(), message);
    }

    protected boolean canBroadcast() {
        return connected;
    }

    protected abstract BaseAtmosphereListener createAtmosphereListener();

    public void setChatRoom(ChatRoomBase chatRoom) {
        this.chatRoom = chatRoom;
    }
}

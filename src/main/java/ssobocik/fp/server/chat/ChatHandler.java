package ssobocik.fp.server.chat;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.gwt.server.AtmosphereGwtHandler;
import org.atmosphere.gwt.server.GwtAtmosphereResource;
import org.atmosphere.gwt.server.JSONDeserializer;
import org.atmosphere.gwt.server.SerializationException;
import org.atmosphere.gwt.server.impl.GwtRpcDeserializer;
import org.atmosphere.gwt.shared.SerialMode;
import ssobocik.fp.client.atmosphere.messages.ChatMessage;
import ssobocik.fp.server.bl.ChatBl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles all communication through Atmosphere framework.
 * @author szymon.sobocik
 */
public class ChatHandler extends AtmosphereGwtHandler {


    private ChatBl chatBl;

    public void setChatBl(ChatBl chatBl) {
        this.chatBl = chatBl;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("org.atmosphere.gwt").setLevel(Level.ALL);
        Logger.getLogger("org.atmosphere.samples").setLevel(Level.ALL);
        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);
        logger.trace("Updated logging levels");
//        setHeartbeat(20000);

    }

    @Override
    public int doComet(GwtAtmosphereResource resource) throws ServletException, IOException {
        String room = resource.getRequest().getPathInfo();
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(room, true);
        resource.getAtmosphereResource().setBroadcaster(broadcaster);
        logger.info("broadcaster: " + broadcaster);
        return 300000;
    }

    @Override
    public void cometTerminated(GwtAtmosphereResource cometResponse, boolean serverInitiated) {
        super.cometTerminated(cometResponse, serverInitiated);
        logger.info("Comet disconnected");
    }

    @Override
    public void doPost(HttpServletRequest postRequest, HttpServletResponse postResponse,
                       List<?> messages, GwtAtmosphereResource cometResource) {
        broadcast(messages, cometResource);
    }

    public ChatHandler() {
        super();
        logger.info("Constructor");
        chatBl = ApplicationContextProvider.getApplicationContext().getBean(ChatBl.class);
        logger.info("Inject spring bean manually, since this handler is constructed outside of spring");
    }

    @Override
    protected GwtAtmosphereResource lookupResource(String connectionUUID) {
        return super.lookupResource(connectionUUID);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        super.destroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setEscapeText(boolean escapeText) {
        super.setEscapeText(escapeText);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        logger.info(resource.toString());
        super.onRequest(resource);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void doServerMessage(HttpServletRequest request, HttpServletResponse response, String connectionUUID) throws IOException {
        logger.info("Request: " + request + "\nresponse: " + response + " \n connectionUUID: " + connectionUUID);
        super.doServerMessage(request, response, connectionUUID);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected SerialMode getDefaultSerialMode() {
        return super.getDefaultSerialMode();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Object deserialize(char[] data, SerialMode mode) throws SerializationException {
        return super.deserialize(data, mode);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Object deserialize(String data, SerialMode mode) throws SerializationException {
        return super.deserialize(data, mode);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected GwtRpcDeserializer getGwtRpc() {
        return super.getGwtRpc();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected JSONDeserializer getJSONDeserializer() {
        return super.getJSONDeserializer();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void broadcast(Object message, GwtAtmosphereResource resource) {
        logger.info("Message: " + message + "\nresource: " + resource );
        if (message instanceof ChatMessage){
            ChatMessage chatMessage = (ChatMessage) message;
            chatBl.saveMessage(chatMessage);
        }
        super.broadcast(message, resource);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void broadcast(List<?> messages, GwtAtmosphereResource resource) {
        logger.info("Message: " + messages + "\nresource: " + resource );
        super.broadcast(messages, resource);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void disconnect(GwtAtmosphereResource resource) {
        logger.info("resource: " + resource );
        super.disconnect(resource);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void execute(Runnable command) {
        logger.info("Command: " + command);
        super.execute(command);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected int computeHeartbeat(int requestedHeartbeat) {
        logger.info("hearbeatRequested: " + requestedHeartbeat);
        int computedHeartbead = super.computeHeartbeat(requestedHeartbeat);
        logger.info("heartbeatComputed: " + computedHeartbead);
        return computedHeartbead;
    }

    @Override
    public void terminate(GwtAtmosphereResource cometResponse, boolean serverInitiated) {
        logger.info("cometResponse: " + cometResponse + "\nserverInitiaded: " + serverInitiated);
        super.terminate(cometResponse, serverInitiated);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {
        logger.info("Event.message: " + event.getMessage());
        super.onStateChange(event);    //To change body of overridden methods use File | Settings | File Templates.
    }
}


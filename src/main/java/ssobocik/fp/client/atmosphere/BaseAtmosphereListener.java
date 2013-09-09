package ssobocik.fp.client.atmosphere;

import com.google.gwt.user.client.rpc.StatusCodeException;
import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author szymon.sobocik
 */
public abstract class BaseAtmosphereListener implements AtmosphereListener{

    private AtmosphereClient client;

    public void setClient(AtmosphereClient client) {
        this.client = client;
    }

    static final Logger logger = Logger.getLogger(BaseAtmosphereListener.class.getName());

    @Override
    public void onError(Throwable exception, boolean connected) {
        int statuscode = -1;
        if (exception instanceof StatusCodeException) {
            statuscode = ((StatusCodeException) exception).getStatusCode();
        }
        logger.log(Level.SEVERE, "comet.error [connected=" + connected + "] (" + statuscode + ")", exception);
        //todo error handling
//            addChatLine(MESSAGE_ROOM_ERROR + exception.getMessage(), COLOR_SYSTEM_MESSAGE);
    }

    @Override
    public void onHeartbeat() {
        logger.info("comet.heartbeat [" + client.getConnectionUUID() + "]");
    }

    @Override
    public void onRefresh() {
        logger.info("comet.refresh [" + client.getConnectionUUID() + "]");
    }

    @Override
    public void onAfterRefresh(String s) {
        logger.info("comet.onAfterRefresh [" + client.getConnectionUUID() + "]" + s);
    }

}

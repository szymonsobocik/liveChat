package ssobocik.fp.client;

import com.google.gwt.core.client.GWT;
import ssobocik.fp.client.atmosphere.ShoutboxAtmosphere;
import ssobocik.fp.client.view.desktop.Desktop;
import ssobocik.fp.dto.StatusDTO;
import ssobocik.fp.dto.UserDTO;

/**
 * Holds application wide client settings
 * like signedInUser, status and messagesBundle
 *
 * @author szymon.sobocik
 */
public class FClientUtils {

    private static FClientUtils ref;
    public static Messages messages = GWT.create(Messages.class);

    private UserDTO signedInUser;
    /**
     * Status of signed user
     */
    private StatusDTO status;

    public synchronized static FClientUtils getInstance() {
        if (ref == null) {
            ref = new FClientUtils();
        }
        return ref;
    }

    public FClientUtils() {
        status = new StatusDTO();
    }

    public UserDTO getSignedInUser() {
        return signedInUser;
    }

    public void setSignedInUser(UserDTO signedInUser) {
        this.signedInUser = signedInUser;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatusConnected() {
        this.status.setConnected(true);
        Desktop.getInstance().refreshStatus();
        ShoutboxAtmosphere.getInstance().informAboutMyStatus();
    }

    public void setStatusDisconnected() {
        this.status.setConnected(false);
        Desktop.getInstance().refreshStatus();
        ShoutboxAtmosphere.getInstance().informAboutMyStatus();
    }

    public boolean isConnected() {
        return signedInUser != null && status != null && status.isConnected();
    }

    public boolean isConnectedThisUser(Integer requestedId) {
        return isConnected() && signedInUser.getId().equals(requestedId);
    }

    public void setDescription(String newDescription) {
        getStatus().setDescription(newDescription);
        ShoutboxAtmosphere.getInstance().informAboutMyStatus();
    }

    public void userSignetOut() {
        setSignedInUser(null);
        this.status = new StatusDTO();
    }
}

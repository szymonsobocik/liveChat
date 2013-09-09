package ssobocik.fp.client.view.user;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.events.CreateNewChatEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.dto.StatusDTO;
import ssobocik.fp.dto.UserDTO;

import java.util.logging.Logger;

/**
 * Holds user and his status. Represents panel on the left.
 * @author szymon.sobocik
 */
public class UserPanel extends Composite {

    static final Logger logger = Logger.getLogger(UserPanel.class.getName());

    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, UserPanel> {
    }

    @UiField
    Label name;

    @UiField
    Label status;

    @UiField
    Label description;

    private UserDTO user;
    private StatusDTO userStatus;

    public UserPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void loadUserData() {
        name.setText(user != null ? user.getUsername() : "");
        if (userStatus != null) {
            status.setText(userStatus.isConnected() ? FClientUtils.messages.lbStatusConnected() : FClientUtils.messages.lbStatusDisconnected());
            description.setText(userStatus.getDescription());
        } else {
            status.setText(FClientUtils.messages.lbStatusDisconnected());
            description.setText("");
        }
    }

    @UiHandler("beginChat")
    void handleClickBeginChat(ClickEvent e) {
        logger.info("Begin chat with user: " + user.getUsername());
        if (user == null || userStatus == null || !userStatus.isConnected()){
            Window.alert(FClientUtils.messages.lbUserShouldBeConnected());
            return;
        }
        InitEvents.EVENT_BUS.fireEvent(new CreateNewChatEvent(user));
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public StatusDTO getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(StatusDTO userStatus) {
        this.userStatus = userStatus;
        loadUserData();
    }
}

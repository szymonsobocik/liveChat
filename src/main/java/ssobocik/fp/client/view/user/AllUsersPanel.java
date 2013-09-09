package ssobocik.fp.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.atmosphere.ShoutboxAtmosphere;
import ssobocik.fp.client.events.AllUsersLoadedEvent;
import ssobocik.fp.client.events.ClearUserStatusesEvent;
import ssobocik.fp.client.events.RefreshUserStatusEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.dto.UserStatusDTO;

import java.util.List;
import java.util.logging.Logger;

/**
 * Displays all users and their statuses.
 * @author szymon.sobocik
 */
public class AllUsersPanel extends Composite {
    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, AllUsersPanel> {
    }

    static final Logger logger = Logger.getLogger(AllUsersPanel.class.getName());

    @UiField
    HTMLPanel usersContainer;

    public AllUsersPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        InitEvents.EVENT_BUS.addHandler(AllUsersLoadedEvent.TYPE, new AllUsersLoadedEvent.Handler() {
            @Override
            public void handle(AllUsersLoadedEvent event) {
                usersLoaded(event.getAllUsers());
            }
        });
        InitEvents.EVENT_BUS.addHandler(RefreshUserStatusEvent.TYPE, new RefreshUserStatusEvent.Handler() {
            @Override
            public void handle(RefreshUserStatusEvent event) {
                refreshUserStatus(event.getUserStatusDTO());
            }
        });
        InitEvents.EVENT_BUS.addHandler(ClearUserStatusesEvent.TYPE, new ClearUserStatusesEvent.Handler() {
            @Override
            public void handle(ClearUserStatusesEvent event) {
                clearUserStatuses();
            }
        });
    }

    private void usersLoaded(List<UserDTO> allUsers) {
        usersContainer.clear();
        for (UserDTO user : allUsers) {
            UserPanel userPanel = new UserPanel();
            userPanel.setUser(user);
            userPanel.loadUserData();
            usersContainer.add(userPanel);
        }
        ShoutboxAtmosphere.getInstance().checkConnectedUsers();
    }

    private void refreshUserStatus(UserStatusDTO userStatusDTO) {
        logger.info("Refreshing user status");
        if (usersContainer != null && userStatusDTO != null) {
            for (int i = 0; i < usersContainer.getWidgetCount(); i++) {
                Widget widget = usersContainer.getWidget(i);
                if (widget instanceof UserPanel) {
                    UserPanel userPanel = (UserPanel) widget;
                    logger.info("Found user panel");
                    if (userPanel.getUser().getId().equals(userStatusDTO.getUserDTO().getId())) {
                        userPanel.setUserStatus(userStatusDTO.getStatusDTO());
                    }
                }
            }
        }
    }

    private void clearUserStatuses() {
        logger.info("clearing user statuses");
        if (usersContainer != null) {
            for (int i = 0; i < usersContainer.getWidgetCount(); i++) {
                Widget widget = usersContainer.getWidget(i);
                if (widget instanceof UserPanel) {
                    UserPanel userPanel = (UserPanel) widget;
                    userPanel.setUserStatus(null);
                }
            }
        }
    }
}

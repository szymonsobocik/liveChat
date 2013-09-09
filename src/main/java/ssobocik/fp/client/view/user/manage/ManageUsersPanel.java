package ssobocik.fp.client.view.user.manage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.atmosphere.ShoutboxAtmosphere;
import ssobocik.fp.client.events.AllUsersLoadedEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.rpc.FAsyncCallback;
import ssobocik.fp.dto.UserDTO;

import java.util.List;

/**
 * Holds on the left table to choose user
 * to edit on the right in a form
 * @author szymon.sobocik
 */
public class ManageUsersPanel extends Composite implements UserSelector {

    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, ManageUsersPanel> {
    }

    @UiField
    UsersDataGrid usersDataGrid;

    @UiField
    EditUserPanel editUserPanel;

    public ManageUsersPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        initOnUsersLoadedEventHandler();
        refreshUsers();
        editUserPanel.setUserSelector(this);
        usersDataGrid.setUserSelectedHandler(editUserPanel);
    }

    private void initOnUsersLoadedEventHandler() {
        InitEvents.EVENT_BUS.addHandler(AllUsersLoadedEvent.TYPE, new AllUsersLoadedEvent.Handler() {
            @Override
            public void handle(AllUsersLoadedEvent event) {
                usersLoaded(event.getAllUsers());
            }
        });

    }

    private void usersLoaded(List<UserDTO> allUsers) {
        usersDataGrid.setUsers(allUsers);
    }

    public void refreshUsers() {
         ShoutboxAtmosphere.getInstance().refreshUserListRequest();
    }


}

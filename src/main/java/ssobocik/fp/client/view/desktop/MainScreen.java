package ssobocik.fp.client.view.desktop;

import com.github.gwtbootstrap.client.ui.TabPane;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.atmosphere.BaseAtmosphere;
import ssobocik.fp.client.atmosphere.ChatRoomAtmosphere;
import ssobocik.fp.client.atmosphere.ShoutboxAtmosphere;
import ssobocik.fp.client.events.ConnectToChatEvent;
import ssobocik.fp.client.events.CreateNewChatEvent;
import ssobocik.fp.client.events.LoadAllUsersEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.view.atmosphere.ChatRoom;
import ssobocik.fp.client.view.atmosphere.Shoutbox;
import ssobocik.fp.client.view.user.AllUsersPanel;
import ssobocik.fp.dto.UserDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Screen that holds user panel on the left and chatrooms, including shoutbos on the right in tabPanel
 * @author szymon.sobocik
 */
public class MainScreen extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, MainScreen> {
    }

    @UiField
    Shoutbox shoutbox;

    @UiField
    AllUsersPanel allUsersPanel;

    @UiField
    TabPanel tabPanel;

    /**
     * References to all initialized atmospheres in this Main screen
     * When user signs out all of them must be stopped
     */
    private List<BaseAtmosphere> initializedAtmoshperes = new ArrayList<BaseAtmosphere>();

    public MainScreen() {
        initWidget(uiBinder.createAndBindUi(this));
        initUsersPanel();
        initShoutBoxAtmosphere();
        initCreateNewChatEventHandler();
        initConnectToChatEventHandler();
    }

    private void initCreateNewChatEventHandler() {
        InitEvents.EVENT_BUS.addHandler(CreateNewChatEvent.TYPE, new CreateNewChatEvent.Handler() {
            @Override
            public void handle(CreateNewChatEvent event) {
                createNewChat(event.getUserToTalkTo());
            }
        });
    }

    private void initConnectToChatEventHandler() {
        InitEvents.EVENT_BUS.addHandler(ConnectToChatEvent.TYPE, new ConnectToChatEvent.Handler() {
            @Override
            public void handle(ConnectToChatEvent event) {
                connectToChat(event.getInviteUserMessage().getRoom(), event.getInviteUserMessage().getHost());
            }
        });
    }

    private void initUsersPanel() {
        InitEvents.EVENT_BUS.fireEvent(new LoadAllUsersEvent());
    }

    private void initShoutBoxAtmosphere() {
        ShoutboxAtmosphere.getInstance().setChatRoom(shoutbox);
        ShoutboxAtmosphere.getInstance().initAtmosphere();
        addAtmosphere(ShoutboxAtmosphere.getInstance());
    }

    private void addAtmosphere(BaseAtmosphere atmosphere) {
        initializedAtmoshperes.add(atmosphere);
    }

    private void createNewChat(UserDTO userToTalkTo) {
        //pseudo UUID, because UUID.randomUUID() is not present on GWT client side
        String pseudoUUID = "" + new Date().getTime();

        connectToChat(pseudoUUID, userToTalkTo);
        ShoutboxAtmosphere.getInstance().inviteUserToChat(pseudoUUID, userToTalkTo);
    }

    private void connectToChat(String room, UserDTO otherSpeaker) {
        ChatRoom chatRoom = new ChatRoom();
        ChatRoomAtmosphere chatRoomAtmosphere = new ChatRoomAtmosphere(room, chatRoom);
        chatRoomAtmosphere.setOtherSpeaker(otherSpeaker);
        chatRoom.setChatRoomAtmosphere(chatRoomAtmosphere);
        chatRoomAtmosphere.initAtmosphere();
        addAtmosphere(chatRoomAtmosphere);
        TabPane newTabPane = new TabPane();
        newTabPane.setHeading(otherSpeaker.getUsername());
        newTabPane.add(chatRoom);
        tabPanel.add(newTabPane);
    }

    public void stopAllAtmoshperes(){

        for (BaseAtmosphere initializedAtmoshpere : initializedAtmoshperes) {
            initializedAtmoshpere.stopAtmosphere();
        }
    }
}


package ssobocik.fp.client.view.desktop;

import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavText;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.events.ShowSignInFormEvent;
import ssobocik.fp.client.events.SignOutEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.view.user.NewUserForm;
import ssobocik.fp.client.view.user.NotSignedInPanelInfo;
import ssobocik.fp.client.view.user.SignInForm;
import ssobocik.fp.client.view.user.changeDescription.ChangeDescriptionDialogBox;
import ssobocik.fp.client.view.user.manage.ManageUsersPanel;
import ssobocik.fp.dto.StatusDTO;

import java.util.logging.Logger;

/**
 * Main screen of an application.
 * Holds navbar and rest of content.
 * Desktop serves as a Singleton.
 *
 * @author szymon.sobocik
 */
public class Desktop extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);


    interface Binder extends UiBinder<Widget, Desktop> {
    }

    static final Logger logger = Logger.getLogger(Desktop.class.getName());
    private static Desktop ref;

    public synchronized static Desktop getInstance() {
        if (ref == null) {
            ref = new Desktop();
        }
        return ref;
    }


    @UiField
    SimplePanel contentPanel;

    @UiField
    Nav navNotSignedIn;

    @UiField
    Nav navSignedIn;

    @UiField
    Nav navMain;

    @UiField
    NavText userName;

    @UiField
    NavText status;

    @UiField
    NavLink manageUsers;


    private MainScreen mainScreen;

    private ManageUsersPanel manageUsersPanel;

    NotSignedInPanelInfo notSignedInPanelInfo = new NotSignedInPanelInfo();

    private Desktop() {
        initWidget(uiBinder.createAndBindUi(this));
        showUserNotSignedIn();
    }

    @UiHandler("signIn")
    void handleClickSignIn(ClickEvent e) {
        InitEvents.EVENT_BUS.fireEvent(new ShowSignInFormEvent());
    }

    @UiHandler("signOut")
    void handleClickSignOut(ClickEvent e) {
        InitEvents.EVENT_BUS.fireEvent(new SignOutEvent());
        logger.info("clicked signOut");
    }

    @UiHandler("displayChats")
    void handleClickDisplayChats(ClickEvent e) {
        showMainScreen();
    }

    @UiHandler("manageUsers")
    void handleClickManageUsers(ClickEvent e) {
        logger.info("clicked manageUsers");
        showManageUsers();
    }

    @UiHandler("changeStatusConnect")
    void handleClickChangeStatusConnect(ClickEvent e) {
        FClientUtils.getInstance().setStatusConnected();
    }

    @UiHandler("changeStatusDisconnected")
    void handleClickChangeStatusDisconnect(ClickEvent e) {
        FClientUtils.getInstance().setStatusDisconnected();
    }

    @UiHandler("changeDescription")
    void handleClickChangeDescription(ClickEvent e) {
        ChangeDescriptionDialogBox dialogBox = new ChangeDescriptionDialogBox();
        dialogBox.center();
        dialogBox.show();
    }

    private void showUserNotSignedIn() {
        contentPanel.setWidget(notSignedInPanelInfo);
    }

    public void showSignInForm() {
        contentPanel.setWidget(new SignInForm());
    }

    public void showNewUserForm() {
        contentPanel.setWidget(new NewUserForm());
    }

    public void userSignedIn() {
        logger.info("user signed in");
        setNavBarUserSignedIn();
        showMainScreen();
    }

    public void userSignedOut(){
        if (mainScreen != null){
            logger.info("stopping all atmospheres");
            mainScreen.stopAllAtmoshperes();
        }
        mainScreen = null;
        setNavBarUserSignedOut();
        showUserNotSignedIn();
    }

    private void showMainScreen() {
        contentPanel.setWidget(getMainScreen());
    }

    private void setNavBarUserSignedIn() {
        navNotSignedIn.setVisible(false);
        navSignedIn.setVisible(true);
        userName.setText(FClientUtils.getInstance().getSignedInUser().getUsername());
        navMain.setVisible(true);
        manageUsers.setVisible(FClientUtils.getInstance().getSignedInUser().isAdmin());
    }

    private void setNavBarUserSignedOut() {
        navNotSignedIn.setVisible(true);
        navSignedIn.setVisible(false);
        navMain.setVisible(false);
    }

    public void refreshStatus() {
        StatusDTO userStatus = FClientUtils.getInstance().getStatus();
        status.setText(userStatus.isConnected() ? FClientUtils.messages.lbStatusConnected() : FClientUtils.messages.lbStatusDisconnected());
    }

    public MainScreen getMainScreen() {
        if (mainScreen == null) {
            mainScreen = new MainScreen();
        }
        return mainScreen;
    }

    private void showManageUsers() {
        contentPanel.setWidget(getManageUsersPanel());
    }

    public ManageUsersPanel getManageUsersPanel() {
        if (manageUsersPanel == null){
            manageUsersPanel = new ManageUsersPanel();
        }
        return manageUsersPanel;
    }

}

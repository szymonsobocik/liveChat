package ssobocik.fp.client.view.user;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.events.ShowNewUserFormEvent;
import ssobocik.fp.client.events.SignedInEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.rpc.FAsyncCallback;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.exceptions.BadUserCredentialsException;
import ssobocik.fp.exceptions.UserNotFoundException;

/**
 * Allows user to sign in
 *
 * @author szymon.sobocik
 */
public class SignInForm extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, SignInForm> {
    }

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox password;

    @UiField
    ControlGroup controlGroupUsername;

    @UiField
    HelpInline helpUsername;

    @UiField
    AlertBlock alert;

    @UiField
    AlertBlock wrongUsernameOrPasswordAlert;


    public SignInForm() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("createNewUser")
    void handleClickCreateNewUser(ClickEvent e) {
        InitEvents.EVENT_BUS.fireEvent(new ShowNewUserFormEvent());
    }

    @UiHandler("username")
    void keyDownUsername(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            signIn();
        }
    }

    @UiHandler("password")
    void keyDownPassword(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            signIn();
        }
    }

    @UiHandler("signIn")
    void handleClickSignIn(ClickEvent e) {
        signIn();
    }

    private void signIn() {
        alert.setVisible(false);
        wrongUsernameOrPasswordAlert.setVisible(false);
        if (!checkUsername()) return;
        InitEvents.getInstance().getService().singIn(username.getText(), password.getText(), new FAsyncCallback<UserDTO>() {
            @Override
            public void onFailureFP(Throwable throwable) {
                if (throwable instanceof UserNotFoundException) {
                    handleNoUser();
                } else if (throwable instanceof BadUserCredentialsException) {
                    handleWrongUsernameOrPassword(throwable.getMessage());
                } else {
                    super.onFailureFP(throwable);
                }
            }

            @Override
            public void onSuccessFP(UserDTO userDTO) {
                FClientUtils.getInstance().setSignedInUser(userDTO);
                InitEvents.EVENT_BUS.fireEvent(new SignedInEvent());
            }
        });
    }

    private void handleWrongUsernameOrPassword(String message) {
        wrongUsernameOrPasswordAlert.setVisible(true);
        wrongUsernameOrPasswordAlert.setHeading(message);
    }

    private boolean checkUsername() {
        String name = username.getText();
        if (name == null || name.isEmpty()) {
            markIsValid(controlGroupUsername, helpUsername, ControlGroupType.ERROR, FClientUtils.messages.errorUsernameIsEmpty());
            return false;
        }
        markIsValid(controlGroupUsername, helpUsername, ControlGroupType.NONE, "");
        return true;
    }

    private void handleNoUser() {
        alert.setVisible(true);
    }

    private void markIsValid(ControlGroup controlGroupUsername, HelpInline helpField, ControlGroupType btnType, String helpText) {
        controlGroupUsername.setType(btnType);
        helpField.setText(helpText);
    }

}

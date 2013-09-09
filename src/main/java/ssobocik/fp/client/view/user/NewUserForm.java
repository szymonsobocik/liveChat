package ssobocik.fp.client.view.user;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.events.ShowSignInFormEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.rpc.FAsyncCallback;
import ssobocik.fp.dto.UserDTO;

/**
 * Gathers data for creation of new user.
 *
 * @author szymon.sobocik
 */
public class NewUserForm extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, NewUserForm> {
    }

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox password;
    @UiField
    PasswordTextBox passwordRepeat;

    @UiField
    ControlGroup controlGroupUsername;

    @UiField
    ControlGroup controlGroupPassword;

    @UiField
    ControlGroup controlGroupPasswordRepeat;

    @UiField
    HelpInline helpUsername;
    @UiField
    HelpInline helpPassword;
    @UiField
    HelpInline helpPasswordRepeat;

    @UiField
    AlertBlock alert;

    public NewUserForm() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("create")
    void handleClickCreate(ClickEvent e) {
        alert.setVisible(false);
        if (!checkUsername()) return;
        if (!checkPasswords()) return;
        InitEvents.getInstance().getService().createNewUser(username.getText(), password.getText(), new FAsyncCallback<UserDTO>() {
            @Override
            public void onSuccessFP(UserDTO userDTO) {
                userCreated(userDTO);
            }
        });
    }

    private void userCreated(UserDTO userDTO) {
        alert.setVisible(true);
    }

    private boolean checkPasswords() {
        String pass = password.getText();
        if (pass == null || pass.isEmpty()) {
            markError(controlGroupPassword, helpPassword, FClientUtils.messages.errorPasswordIsEmpty());
            return false;
        }
        markOk(controlGroupPassword, helpPassword);

        pass = passwordRepeat.getText();
        if (pass == null || pass.isEmpty()) {
            markError(controlGroupPasswordRepeat, helpPasswordRepeat, FClientUtils.messages.errorPasswordIsEmpty());
            return false;
        }
        markOk(controlGroupPasswordRepeat, helpPasswordRepeat);

        if (!password.getText().equals(passwordRepeat.getText())) {
            markError(controlGroupPassword, helpPassword, FClientUtils.messages.errorPasswordsDontMatch());
            return false;
        }
        markOk(controlGroupPassword, helpPassword);
        return true;
    }

    private boolean checkUsername() {
        String name = username.getText();
        if (name == null || name.isEmpty()) {
            markError(controlGroupUsername, helpUsername, FClientUtils.messages.errorUsernameIsEmpty());
            return false;
        }
        markOk(controlGroupUsername, helpUsername);
        return true;
    }

    private void markOk(ControlGroup controlGroup, HelpInline helpField) {
        markIsValid(controlGroup, helpField, ControlGroupType.NONE, "");
    }

    private void markError(ControlGroup controlGroup, HelpInline helpField, String message) {
        markIsValid(controlGroup, helpField, ControlGroupType.ERROR, message);
    }

    private void markIsValid(ControlGroup controlGroup, HelpInline helpField, ControlGroupType btnType, String helpText) {
        controlGroup.setType(btnType);
        helpField.setText(helpText);
    }

    @UiHandler("signIn")
    void handleClickSignIn(ClickEvent e) {
        InitEvents.EVENT_BUS.fireEvent(new ShowSignInFormEvent());
    }
}

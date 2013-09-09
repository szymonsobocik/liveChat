package ssobocik.fp.client.view.user.manage;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.events.LoadAllUsersEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;
import ssobocik.fp.client.rpc.FAsyncCallback;
import ssobocik.fp.dto.UserDTO;

/**
 * Allows editing selected user
 *
 * @author szymon.sobocik
 */
public class EditUserPanel extends Composite implements UserSelectedHandler, Editor<UserDTO> {

    private static Binder uiBinder = GWT.create(Binder.class);

    private ManageUsersPanel userSelector;

    interface Binder extends UiBinder<Widget, EditUserPanel> {
    }

    @UiField
    TextBox username;

    @UiField
    TextBox nickname;

    @UiField
    PasswordTextBox password;

    @UiField
    CheckBox admin;

    @UiField
    ControlGroup usernameControlGroup;

    @UiField
    ControlGroup passwordControlGroup;

    @UiField
    @Ignore
    HelpInline usernameHelpInline;

    @UiField
    @Ignore
    HelpInline passwordHelpInline;

    @UiField
    AlertBlock alert;

    @UiField
    WellForm userForm;

    interface EditorDriver extends SimpleBeanEditorDriver<UserDTO, EditUserPanel> {
    }

    EditorDriver editorDriver = GWT.create(EditorDriver.class);

    private UserDTO userToEdit;

    public EditUserPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        editorDriver.initialize(this);
        createNewUser();
    }

    @UiHandler("saveButton")
    void handleClickSaveChanges(ClickEvent e) {
        alert.setVisible(false);
        if (!validate()) return;
        UserDTO editedUser = editorDriver.flush();
        InitEvents.getInstance().getService().saveUser(editedUser, new FAsyncCallback<UserDTO>() {
            @Override
            public void onSuccessFP(UserDTO userDTO) {
                userSaved(userDTO);
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        if (!checkUsername()) isValid = false;
        if (!checkPassword()) isValid = false;
        return isValid;
    }

    private void userSaved(UserDTO userDTO) {
        actionCompleted(FClientUtils.messages.lbUserSaved());
    }

    private void userRemoved() {
        actionCompleted(FClientUtils.messages.lbUserRemoved());
    }

    private void actionCompleted(String result) {
        alert.setVisible(true);
        alert.setText(result);
        createNewUser();
        if (userSelector != null) {
            userSelector.refreshUsers();
        }
    }

    private boolean checkPassword() {
        String pass = password.getText();
        if (pass == null || pass.isEmpty()) {
            markError(passwordControlGroup, passwordHelpInline, FClientUtils.messages.errorPasswordIsEmpty());
            return false;
        }
        markOk(passwordControlGroup, passwordHelpInline);
        return true;
    }

    private boolean checkUsername() {
        String name = username.getText();
        if (name == null || name.isEmpty()) {
            markError(usernameControlGroup, usernameHelpInline, FClientUtils.messages.errorUsernameIsEmpty());
            return false;
        }
        markOk(usernameControlGroup, usernameHelpInline);
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

    @Override
    public void selectedUser(UserDTO userDTO) {
        this.userToEdit = userDTO;
        editorDriver.edit(userDTO);
        markOk(usernameControlGroup, usernameHelpInline);
        markOk(passwordControlGroup, passwordHelpInline);
    }

    @UiHandler("cancelButton")
    public void onCancelClick(ClickEvent e) {
        editorDriver.edit(userToEdit);
    }

    @UiHandler("newUserButton")
    public void onNewUserClick(ClickEvent e) {
        createNewUser();
    }

    private void createNewUser() {
        editorDriver.edit(new UserDTO());
    }

    @UiHandler("removeButton")
    public void onRemoveClick(ClickEvent e) {
        InitEvents.getInstance().getService().removeUser(userToEdit, new FAsyncCallback<Void>() {
            @Override
            public void onSuccessFP(Void aVoid) {
                userRemoved();
            }
        });
    }

    public void setUserSelector(ManageUsersPanel userSelector) {
        this.userSelector = userSelector;
    }

}


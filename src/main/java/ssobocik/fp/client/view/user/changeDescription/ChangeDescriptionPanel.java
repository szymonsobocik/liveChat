package ssobocik.fp.client.view.user.changeDescription;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;

/**
 * @author szymon.sobocik
 */
public class ChangeDescriptionPanel extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);
    private DialogBox dialogBox;

    public void setDialogBox(DialogBox dialogBox) {
        this.dialogBox = dialogBox;
    }

    interface Binder extends UiBinder<Widget, ChangeDescriptionPanel> {
    }

    public ChangeDescriptionPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    TextArea descriptionTextArea;

    @UiHandler("changeButton")
    void onChange(ClickEvent e) {
        FClientUtils.getInstance().setDescription(descriptionTextArea.getText());
        dialogBox.hide();
    }

    @UiHandler("cancelButton")
    void onCancel(ClickEvent e) {
        dialogBox.hide();
    }
}

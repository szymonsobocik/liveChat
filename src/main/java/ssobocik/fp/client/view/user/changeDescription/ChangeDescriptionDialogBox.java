package ssobocik.fp.client.view.user.changeDescription;

import com.google.gwt.user.client.ui.DialogBox;

/**
 * @author szymon.sobocik
 */
public class ChangeDescriptionDialogBox extends DialogBox {

    public ChangeDescriptionDialogBox() {
        super(true);
        setText("Change description");
        setGlassEnabled(true);
        setAnimationEnabled(true);
        ChangeDescriptionPanel changeDescriptionPanel = new ChangeDescriptionPanel();
        changeDescriptionPanel.setDialogBox(this);
        setWidget(changeDescriptionPanel);
    }
}

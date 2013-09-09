package ssobocik.fp.client.view.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.events.ShowSignInFormEvent;
import ssobocik.fp.client.events.initEvents.InitEvents;

/**
 * Informs user, that he should log in
 *
 * @author szymon.sobocik
 */
public class NotSignedInPanelInfo extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);

    interface Binder extends UiBinder<Widget, NotSignedInPanelInfo> {
    }

    public NotSignedInPanelInfo() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("signIn")
    void handleClickSignIn(ClickEvent e) {
        InitEvents.EVENT_BUS.fireEvent(new ShowSignInFormEvent());
    }
}

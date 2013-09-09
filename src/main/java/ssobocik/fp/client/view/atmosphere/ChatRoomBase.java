package ssobocik.fp.client.view.atmosphere;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.atmosphere.messages.ChatMessage;

/**
 * Base for Shoutbox and other chatrooms
 *
 * @author szymon.sobocik
 */
public abstract class ChatRoomBase extends Composite {

    private static Binder uiBinder = GWT.create(Binder.class);


    interface Binder extends UiBinder<Widget, ChatRoomBase> {
    }

    DateTimeFormat timeFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_MEDIUM);

    @UiField
    TextArea messageInput;

    @UiField
    HTMLPanelAutoScroll conversation;

    public ChatRoomBase() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("send")
    void handleClick(ClickEvent e) {
        sendMessageBase();
    }

    @UiHandler("messageInput")
    void keyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            sendMessageBase();
        }
    }

    private void sendMessageBase() {
        sendMessage(messageInput.getText());
        messageInput.setText("");
    }

    protected abstract void sendMessage(String message);

    public void receivedMessage(ChatMessage e) {
        String line = timeFormat.format(e.getTime())
                + " <b>" + e.getAuthor().getUsername() + "</b> " + e.getMessage();
        if (e.getAuthor().getId().equals(FClientUtils.getInstance().getSignedInUser().getId())) {
            addChatLine(line, Colors.COLOR_MESSAGE_SELF);
        } else {
            addChatLine(line, Colors.COLOR_MESSAGE_OTHERS);
        }
    }

    void addChatLine(String line, String color) {
        HTML newLine = new HTML(line);
        newLine.getElement().getStyle().setColor(color);
        conversation.add(newLine);
        newLine.getElement().scrollIntoView();
    }
}

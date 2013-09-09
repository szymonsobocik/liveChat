package ssobocik.fp.client.view.atmosphere;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Automatically scrolls to bottom, after adding new widget
 *
 * @author szymon.sobocik
 */
public class HTMLPanelAutoScroll extends HTMLPanel {

    public HTMLPanelAutoScroll(String html) {
        super(html);
    }

    public HTMLPanelAutoScroll(SafeHtml safeHtml) {
        super(safeHtml);
    }

    public HTMLPanelAutoScroll(String tag, String html) {
        super(tag, html);
    }

    @Override
    public void add(Widget widget) {
        super.add(widget);
        widget.getElement().scrollIntoView();
    }
}

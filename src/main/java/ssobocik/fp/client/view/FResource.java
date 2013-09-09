package ssobocik.fp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Holds resources for client side
 *
 * @author szymon.sobocik
 */
public interface FResource extends ClientBundle{

    public static FResource INSTANCE = GWT.create(FResource.class);

    @CssResource.NotStrict
    @Source("mainStyle.css")
    CssResource cssMain();
}

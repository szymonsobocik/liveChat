package ssobocik.fp.client.events.initEvents;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import ssobocik.fp.client.FClientUtils;
import ssobocik.fp.client.events.*;
import ssobocik.fp.client.rpc.FAsyncCallback;
import ssobocik.fp.client.rpc.FPService;
import ssobocik.fp.client.rpc.FPServiceAsync;
import ssobocik.fp.client.view.desktop.Desktop;
import ssobocik.fp.dto.UserDTO;

import java.util.List;
import java.util.logging.Logger;

/**
 * Major class that redirects event flow on client sida
 * and holds RPC service
 * Becouse this is singleton it's possible to call RPC from everywhere
 *
 * @author szymon.sobocik
 */
public class InitEvents extends AbstractInitEvents<FPServiceAsync> {

    public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);

    private static InitEvents ref;
    static final Logger logger = Logger.getLogger(InitEvents.class.getName());

    public synchronized static InitEvents getInstance() {
        if (ref == null) {
            ref = new InitEvents();
        }
        return ref;
    }

    public InitEvents() {
        setService((FPServiceAsync) GWT.create(FPService.class));
        initSingInOutEvents();
       initNewUserEvents();
        initUsersEvents();
    }



    private void initSingInOutEvents() {
        EVENT_BUS.addHandler(ShowSignInFormEvent.TYPE, new ShowSignInFormEvent.Handler() {
            @Override
            public void handle(final ShowSignInFormEvent event) {
                logger.info("clicked signIn");
                UserDTO signedInUser = FClientUtils.getInstance().getSignedInUser();
                if (signedInUser != null){
                    Window.alert(FClientUtils.messages.lbUserAlreadySignedIn());
                } else {
                    Desktop.getInstance().showSignInForm();
                }
            }
        });

        EVENT_BUS.addHandler(SignedInEvent.TYPE, new SignedInEvent.Handler() {
            @Override
            public void handle(final SignedInEvent event) {
                Desktop.getInstance().userSignedIn();
            }
        });

        EVENT_BUS.addHandler(SignOutEvent.TYPE, new SignOutEvent.Handler() {
            @Override
            public void handle(final SignOutEvent event) {
                Desktop.getInstance().userSignedOut();
                FClientUtils.getInstance().userSignetOut();
            }
        });
    }

    private void initNewUserEvents() {
        EVENT_BUS.addHandler(ShowNewUserFormEvent.TYPE, new ShowNewUserFormEvent.Handler() {
            @Override
            public void handle(final ShowNewUserFormEvent event) {
                Desktop.getInstance().showNewUserForm();
            }
        });

    }

    private void initUsersEvents() {
        EVENT_BUS.addHandler(LoadAllUsersEvent.TYPE, new LoadAllUsersEvent.Handler() {
            @Override
            public void handle(final LoadAllUsersEvent event) {
                getService().getAllUsers(new FAsyncCallback<List<UserDTO>>() {
                    @Override
                    public void onSuccessFP(List<UserDTO> allUsersDTO) {
                        EVENT_BUS.fireEvent(new AllUsersLoadedEvent(allUsersDTO));
                    }
                });
            }
        });


    }


}

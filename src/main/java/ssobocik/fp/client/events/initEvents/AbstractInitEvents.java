package ssobocik.fp.client.events.initEvents;

/**
 * Base class for any class that holds RPC service on a client side
 *
 * @author szymon.sobocik
 */
public class AbstractInitEvents<T> implements HasService<T>{

    private T service;

    public void setService(T service) {
        this.service = service;
    }

    public T getService() {
        //todo make here 'please wait'
        return service;
    }
}

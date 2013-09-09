package ssobocik.fp.client.events.initEvents;

/**
 *
 * @author szymon.sobocik
 */
public interface HasService <T> {
    public void setService(T service);
    public T getService();
}

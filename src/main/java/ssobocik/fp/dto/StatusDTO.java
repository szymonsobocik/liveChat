package ssobocik.fp.dto;

import java.io.Serializable;

/**
 * @author szymon.sobocik
 */
public class StatusDTO implements Serializable {
    private boolean connected;
    private String description;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

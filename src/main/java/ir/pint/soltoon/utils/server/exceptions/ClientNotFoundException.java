package ir.pint.soltoon.utils.server.exceptions;

import ir.pint.soltoon.utils.shared.exceptions.SoltoonContainerException;

public class ClientNotFoundException extends SoltoonContainerException {
    private String key;
    private String host;

    public ClientNotFoundException(String key, String host) {
        this.key = key;
        this.host = host;
    }

    public String getKey() {
        return key;
    }

    public String getHost() {
        return host;
    }
}

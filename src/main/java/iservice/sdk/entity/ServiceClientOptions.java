package iservice.sdk.entity;

import java.net.URI;

/**
 * Created by mitch on 2020/9/16.
 */
public class ServiceClientOptions {

    private URI uri;

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public String getHost() {
        return uri.getHost();
    }

    public int getPort() {
        return uri.getPort();
    }

    @Override
    public String toString() {
        return "ServiceClientOptions{" +
                "host='" + getHost() + '\'' +
                ", port=" + getPort() +
                '}';
    }
}

package iservice.sdk.entity;

import java.net.URI;

/**
 * Service client config options
 *
 * @author Yelong
 */
public class ServiceClientOptions {

    private URI uri;

    private SignAlgo signAlgo = SignAlgo.SECP256K1;

    public ServiceClientOptions() {
    }

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

    public SignAlgo getSignAlgo() {
        return signAlgo;
    }

    public void setSignAlgo(SignAlgo signAlgo) {
        this.signAlgo = signAlgo;
    }

}

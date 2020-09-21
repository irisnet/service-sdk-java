package iservice.sdk.entity;

/**
 * Service client config options
 *
 * @author Yelong
 */
public class ServiceClientOptions {
    private SignAlgo signAlgo;

    public ServiceClientOptions() {
    }

    public SignAlgo getSignAlgo() {
        return signAlgo;
    }

    public void setSignAlgo(SignAlgo signAlgo) {
        this.signAlgo = signAlgo;
    }

    @Override
    public String toString() {
        return "ServiceClientOptions{" +
                "signAlgo=" + signAlgo +
                '}';
    }
}

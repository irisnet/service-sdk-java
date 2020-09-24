package iservice.sdk.entity;

import java.net.URI;

/**
 * Service client config options
 *
 * @author Yelong
 */
public class ServiceClientOptions {

    private URI grpcURI;
    private URI rpcURI;

    private SignAlgo signAlgo = SignAlgo.SECP256K1;

    public ServiceClientOptions() {
    }

    public URI getRpcURI() {
        return rpcURI;
    }

    public void setRpcURI(URI rpcURI) {
        this.rpcURI = rpcURI;
    }

    public void setGrpcURI(URI grpcURI) {
        this.grpcURI = grpcURI;
    }

    public URI getGrpcURI() {
        return grpcURI;
    }

    public SignAlgo getSignAlgo() {
        return signAlgo;
    }

    public void setSignAlgo(SignAlgo signAlgo) {
        this.signAlgo = signAlgo;
    }

}

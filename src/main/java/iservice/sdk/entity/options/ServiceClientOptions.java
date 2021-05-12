package iservice.sdk.entity.options;

import iservice.sdk.entity.SignAlgo;

import java.net.URI;

/**
 * Service client config options
 *
 * @author Yelong
 */
public class ServiceClientOptions {

    /**
     * grpc uri
     */
    private URI grpcURI;
    /**
     * websocket uri & http uri
     */
    private URI rpcURI;

    /**
     * rpc client start time out
     * default 15 * {@link java.util.concurrent.TimeUnit#SECONDS}
     */
    private long rpcStartTimeout = 15 * 1000;

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

    public long getRpcStartTimeout() {
        return rpcStartTimeout;
    }

    public void setRpcStartTimeout(long rpcStartTimeout) {
        this.rpcStartTimeout = rpcStartTimeout;
    }
}

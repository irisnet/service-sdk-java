package iservice.sdk.net;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Yelong
 */
public enum GrpcChannel {

    INSTANCE;

    private ManagedChannel channel;

    public ManagedChannel getChannel() {
        return channel;
    }

    public void setURL(String url) {
        ManagedChannelBuilder<?> builder = ManagedChannelBuilder.forTarget(url).usePlaintext();
        this.channel = builder.build();
    }
}
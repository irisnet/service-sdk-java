package iservice.sdk.net;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Yelong
 */
public enum GrpcChannel {

    INSTANCE;

    private ManagedChannel channel;

    /**
     * Get GrpcChannel instance
     * @return
     */
    public static GrpcChannel getInstance() {
        return INSTANCE;
    }

    /**
     * Get Managed GrpcChannel
     * @return
     */
    public ManagedChannel getChannel() {
        return channel;
    }

    public void setURL(String url) {
        ManagedChannelBuilder<?> builder = ManagedChannelBuilder.forTarget(url).usePlaintext();
        this.channel = builder.build();
    }
}
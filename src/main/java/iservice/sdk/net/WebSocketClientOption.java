package iservice.sdk.net;

import java.net.URI;

/**
 *
 *
 * @author : ori
 * @date : 2020/9/21 6:04 下午
 */
public class WebSocketClientOption {
    private URI uri;

    private boolean blockStart;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public boolean isBlockStart() {
        return blockStart;
    }

    public void setBlockStart(boolean blockStart) {
        this.blockStart = blockStart;
    }

    public String getHost() {
        return this.uri.getHost();
    }

    public int getPort() {
        return this.uri.getPort();
    }

    @Override
    public String toString() {
        return "WebSocketClientOption{" +
                "uri=" + uri +
                ", blockStart=" + blockStart +
                '}';
    }
}

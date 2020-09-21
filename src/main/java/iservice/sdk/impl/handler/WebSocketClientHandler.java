package iservice.sdk.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import iservice.sdk.impl.listener.ConnectEventSource;
import iservice.sdk.impl.listener.ServiceClientConnectListenerImpl;
import iservice.sdk.impl.listener.events.ConnectEvent;
import iservice.sdk.impl.listener.events.ConnectEventType;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/9/20 9:16 下午
 */
public class WebSocketClientHandler extends WebSocketClientProtocolHandler {

    private final ConnectEventSource eventSource;

    public WebSocketClientHandler(URI uri) {
        super(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));
        eventSource = new ConnectEventSource();
        eventSource.addListener(new ServiceClientConnectListenerImpl());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("获取到信息了");
        if (msg instanceof TextWebSocketFrame) {
            String json = ((TextWebSocketFrame) msg).text();
            eventSource.notifyAllListeners(new ConnectEvent(eventSource, ConnectEventType.ON_MESSAGE,json));
        }
        super.channelRead(ctx, msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connect is open!");
        eventSource.notifyAllListeners(new ConnectEvent(eventSource, ConnectEventType.ON_OPEN));
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connect is closed!");
        eventSource.notifyAllListeners(new ConnectEvent(eventSource, ConnectEventType.ON_CLOSE));
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("channel exception");
        eventSource.notifyAllListeners(new ConnectEvent(eventSource, ConnectEventType.ON_ERROR));
        super.exceptionCaught(ctx, cause);
    }
}

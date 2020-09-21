package iservice.sdk.impl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import iservice.sdk.impl.observer.events.ConnectEvent;
import iservice.sdk.impl.observer.events.ConnectEventType;
import iservice.sdk.impl.observer.ConnectEventObservable;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/9/20 9:16 下午
 */
public class WebSocketClientHandler extends WebSocketClientProtocolHandler {

    public final static ConnectEventObservable EVENT_OBSERVABLE = new ConnectEventObservable();

    public WebSocketClientHandler(URI uri) {
        super(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Message arrived!");
        if (msg instanceof TextWebSocketFrame) {
            String json = ((TextWebSocketFrame) msg).text();
            EVENT_OBSERVABLE.setChanged();
            EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_MESSAGE,json));
        }
        super.channelRead(ctx, msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connect is open!");
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_OPEN));
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connect is closed!");
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_CLOSE));
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("channel exception");
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_ERROR));
        super.exceptionCaught(ctx, cause);
    }
}

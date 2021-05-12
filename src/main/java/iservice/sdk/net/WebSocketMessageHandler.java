package iservice.sdk.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.exception.WebSocketConnectException;
import iservice.sdk.net.observer.ConnectEventObservable;
import iservice.sdk.net.observer.events.ConnectEvent;
import iservice.sdk.net.observer.events.ConnectEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author : ori
 * @date : 2020/9/20 9:16 下午
 */
public class WebSocketMessageHandler extends WebSocketClientProtocolHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(WebSocketMessageHandler.class);

    public final static ConnectEventObservable EVENT_OBSERVABLE = new ConnectEventObservable();

    private final StringBuffer buffer = new StringBuffer();

    public WebSocketMessageHandler(URI uri) {
        super(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof TextWebSocketFrame) {
                String content = ((TextWebSocketFrame) msg).text();
                if (buffer.length()>0) {
                    throw new WebSocketConnectException("TextWebSocketFrame crash!");
                }
                buffer.append(content);
                if (((TextWebSocketFrame) msg).isFinalFragment()) {
                    doMessageComplete();
                }
            } else if (msg instanceof ContinuationWebSocketFrame) {
                if (buffer.length()>0) {
                    ContinuationWebSocketFrame msgFrame = (ContinuationWebSocketFrame) msg;
                    buffer.append(msgFrame.text());
                } else {
                    throw new ServiceSDKException("Continuation frame received without initial frame.");
                }
                if (((ContinuationWebSocketFrame) msg).isFinalFragment()) {
                    doMessageComplete();
                }
            }
        super.channelRead(ctx, msg);
    }

    private void doMessageComplete() {
        String totalMsg = buffer.toString();
        buffer.delete(0,buffer.length());
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_MESSAGE, totalMsg));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_OPEN));
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_CLOSE));
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        EVENT_OBSERVABLE.setChanged();
        EVENT_OBSERVABLE.notifyObservers(new ConnectEvent(ConnectEventType.ON_ERROR,cause));
    }
}

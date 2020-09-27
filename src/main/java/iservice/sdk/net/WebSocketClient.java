package iservice.sdk.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import iservice.sdk.core.WebSocketClientObserver;
import iservice.sdk.exception.WebSocketConnectException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : ori
 * @date : 2020/9/21 5:46 下午
 */
public class WebSocketClient {
    private static final String ERR_MSG_CHANNEL_INACTIVE = "WebSocket channel inactive";

    private final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, 1, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), r -> new Thread(r, "WebSocket Client Daemon"));

    private final WebSocketClientOptions options;

    private Channel channel = null;

    private CountDownLatch latch;

    /**
     * To record if the client has been started.
     */
    private boolean startedFlag = false;

    public WebSocketClient(WebSocketClientOptions options) {
        this.options = options;

        Runtime.getRuntime().addShutdownHook(new Thread(this.poolExecutor::shutdown));
    }

    public void start() {
        // if client has been started and not be closed, it should not start again
        if (startedFlag) {
            return;
        }
        // param check
        if (options.getUri() == null) {
            return;
        }
        // prepare for start
        prepareStart();
        poolExecutor.execute(this::doConnect);
        try {
            latch.await(options.getStartTimeOut(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isReady()) {
            throw new WebSocketConnectException("WebSocket client start time out.");
        }
    }

    private void doConnect() {
        NioEventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = getBootstrap(workLoopGroup);
            ChannelFuture channelFuture = bootstrap.connect(options.getHost(), options.getPort()).sync();
            // to hold a channel
            channel = channelFuture.channel();
            // notify main thread that the client is start
            latch.countDown();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.err.println("Connect failed.");
            startedFlag = false;
            e.printStackTrace();
            // if catch exceptions, tell main thread unblock immediately
            latch.countDown();
        } finally {
            prepareClose();
            workLoopGroup.shutdownGracefully();
        }
    }

    private void initHandlerObserver() {
        WebSocketMessageHandler.EVENT_OBSERVABLE.deleteObservers();
        WebSocketMessageHandler.EVENT_OBSERVABLE.addObserver(new WebSocketClientObserver());
    }

    /**
     * prepare for start client
     */
    private void prepareStart() {
        this.startedFlag = true;
        synchronized (this) {
            latch = new CountDownLatch(1);
        }
        // init Handler Observer
        initHandlerObserver();
    }

    private void prepareClose() {
        startedFlag = false;
        System.err.println("Websocket Client is closing...");
    }

    private Bootstrap getBootstrap(NioEventLoopGroup workLoopGroup) {
        return new Bootstrap()
                .group(workLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws URISyntaxException {
                        ch.pipeline().addLast(
                                // you can read binary info with this plugin
//                                    new LoggingHandler(LogLevel.INFO),
                                // HttpEncoder & HttpDecoder
                                new HttpClientCodec(),
                                // HttpFile length limiter
                                new HttpObjectAggregator(1024 * 1024 * 10),
                                // custom websocket message handler
                                new WebSocketMessageHandler(new URI(options.getUri().toString() + "/websocket"))
                        );
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true);
    }

    public void reconnect() {
        // ensure channel close
        close();
        tryStart();
    }

    private void tryStart() {
        while (!isReady()) {
            try {
                Thread.sleep(3000);
                start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (!isReady() || !startedFlag) {
            return;
        }
        prepareClose();
        try {
            channel.close();
        } finally {
            startedFlag = false;
        }
    }

    /**
     * check channel is active
     *
     * @param ifThrow
     * @return
     */
    private boolean checkChannelActive(boolean ifThrow) {
        if (channel == null) {
            if (ifThrow) {
                throw new WebSocketConnectException(ERR_MSG_CHANNEL_INACTIVE);
            } else {
                System.err.println(ERR_MSG_CHANNEL_INACTIVE);
                return false;
            }
        }
        return channel.isActive();
    }

    public boolean isReady() {
        return checkChannelActive(false);
    }

    public void send(String msg) {
        if (!isReady()) {
            throw new WebSocketConnectException(ERR_MSG_CHANNEL_INACTIVE);
        }
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

}

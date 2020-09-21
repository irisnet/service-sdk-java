package iservice.sdk.impl;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import iservice.sdk.entity.BaseServiceRequest;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.exception.SdkConnectException;
import iservice.sdk.impl.handler.WebSocketClientHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitch on 2020/9/16.
 */
public final class ServiceClient {

    private ServiceClientOptions optionsCache;
    private ServiceClientOptions options;

    private final List<AbstractServiceListener> LISTENERS_CACHE = new ArrayList<>();
    private final List<AbstractServiceListener> LISTENERS = new ArrayList<>();

    private Channel channel = null;

    private boolean startedFlag = false;

    /**------------ Singleton start------------**/
    private static class ServiceClientHolder{
        private static final ServiceClient INSTANCE = new ServiceClient();
    }
    private ServiceClient() {
    }

    public static ServiceClient getInstance(){
        return ServiceClientHolder.INSTANCE;
    }
    /**------------ Singleton end------------**/

    public void start() {
        // if client has been started and not be closed, it should not start again
        if (startedFlag) {
            return;
        }
        // prepare for start
        initStart();
        NioEventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = getBootstrap(workLoopGroup);
            ChannelFuture channelFuture = bootstrap.connect(options.getHost(), options.getPort()).sync();
            // 持有channel
            channel = channelFuture.channel();
            // 保证会执行关闭操作
            Runtime.getRuntime().addShutdownHook(new Thread(this::close));

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            System.err.println("Connect failed.");
            startedFlag = false;
            e.printStackTrace();
        } finally {
            workLoopGroup.shutdownGracefully();
        }
    }

    /**
     * prepare for start client
     */
    private void initStart() {
        options.setUri(optionsCache.getUri());
        LISTENERS.addAll(LISTENERS_CACHE);
        this.startedFlag = true;
    }

    private Bootstrap getBootstrap(NioEventLoopGroup workLoopGroup) {
        // 建立链接引导器
        return new Bootstrap()
                // 设置工作组
                .group(workLoopGroup)
                // 设置nio信道
                .channel(NioSocketChannel.class)
                // 加入处理器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                // 日志处理器
//                                    new LoggingHandler(LogLevel.INFO),
                                // http编码解码器
                                new HttpClientCodec(),
                                // http对象长度限制器，用于限制http传输对象大小
                                new HttpObjectAggregator(1024 * 1024 * 10),
                                // 默认websocket信息处理器
                                new WebSocketClientHandler(options.getUri())
                        );
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true);
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
                throw new SdkConnectException("Client has not start!");
            } else {
                return false;
            }
        }
        return channel.isActive();
    }

    public boolean isReady() {
        if (checkChannelActive(false)) {
            System.err.println("Client has not start!");
            return false;
        }
        return true;
    }

    public void callService(String method, String id, String query) {
        BaseServiceRequest req = new BaseServiceRequest();
        req.setId(id);
        req.setMethod(method);
        req.setQuery(query);
        callService(req);
    }

    public void callService(BaseServiceRequest req) {
        checkChannelActive(true);
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(req)));
    }

    public void doNotifyAllListener(String msg) {
        // TODO 创建 tx 并签名广播
        System.out.println("Sending request ...");
        System.out.println(msg);

        this.LISTENERS.forEach(listener -> {
            listener.callback(msg);
        });
    }

    public void close() {
        if (channel == null) {
            System.err.println("Client has not start!");
            return;
        } else if (!channel.isActive()) {
            System.err.println("Client is not active! It may have closed already!");
        }
        System.out.println("Channel closed.");
        LISTENERS.clear();
        try {
            channel.close();
        } finally {
            startedFlag = false;
        }
    }

    public void setOptions(ServiceClientOptions options) {
        this.optionsCache = options;
    }

    public void refreshListeners(List<AbstractServiceListener> listeners) {
        clearListeners();
        addListeners(listeners);
    }

    public void addListeners(List<AbstractServiceListener> listeners) {
        this.LISTENERS_CACHE.addAll(listeners);
    }

    public void clearListeners() {
        this.LISTENERS_CACHE.clear();
    }

}

package tendermint.rpc.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.32.1)",
        comments = "Source: tendermint/rpc/grpc/types.proto")
public final class BroadcastAPIGrpc {

    private BroadcastAPIGrpc() {
    }

    public static final String SERVICE_NAME = "tendermint.rpc.grpc.BroadcastAPI";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<tendermint.rpc.grpc.Types.RequestPing,
            tendermint.rpc.grpc.Types.ResponsePing> getPingMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "Ping",
            requestType = tendermint.rpc.grpc.Types.RequestPing.class,
            responseType = tendermint.rpc.grpc.Types.ResponsePing.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<tendermint.rpc.grpc.Types.RequestPing,
            tendermint.rpc.grpc.Types.ResponsePing> getPingMethod() {
        io.grpc.MethodDescriptor<tendermint.rpc.grpc.Types.RequestPing, tendermint.rpc.grpc.Types.ResponsePing> getPingMethod;
        if ((getPingMethod = BroadcastAPIGrpc.getPingMethod) == null) {
            synchronized (BroadcastAPIGrpc.class) {
                if ((getPingMethod = BroadcastAPIGrpc.getPingMethod) == null) {
                    BroadcastAPIGrpc.getPingMethod = getPingMethod =
                            io.grpc.MethodDescriptor.<tendermint.rpc.grpc.Types.RequestPing, tendermint.rpc.grpc.Types.ResponsePing>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ping"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                                            tendermint.rpc.grpc.Types.RequestPing.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                                            tendermint.rpc.grpc.Types.ResponsePing.getDefaultInstance()))
                                    .build();
                }
            }
        }
        return getPingMethod;
    }

    private static volatile io.grpc.MethodDescriptor<tendermint.rpc.grpc.Types.RequestBroadcastTx,
            tendermint.rpc.grpc.Types.ResponseBroadcastTx> getBroadcastTxMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "BroadcastTx",
            requestType = tendermint.rpc.grpc.Types.RequestBroadcastTx.class,
            responseType = tendermint.rpc.grpc.Types.ResponseBroadcastTx.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<tendermint.rpc.grpc.Types.RequestBroadcastTx,
            tendermint.rpc.grpc.Types.ResponseBroadcastTx> getBroadcastTxMethod() {
        io.grpc.MethodDescriptor<tendermint.rpc.grpc.Types.RequestBroadcastTx, tendermint.rpc.grpc.Types.ResponseBroadcastTx> getBroadcastTxMethod;
        if ((getBroadcastTxMethod = BroadcastAPIGrpc.getBroadcastTxMethod) == null) {
            synchronized (BroadcastAPIGrpc.class) {
                if ((getBroadcastTxMethod = BroadcastAPIGrpc.getBroadcastTxMethod) == null) {
                    BroadcastAPIGrpc.getBroadcastTxMethod = getBroadcastTxMethod =
                            io.grpc.MethodDescriptor.<tendermint.rpc.grpc.Types.RequestBroadcastTx, tendermint.rpc.grpc.Types.ResponseBroadcastTx>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BroadcastTx"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                                            tendermint.rpc.grpc.Types.RequestBroadcastTx.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                                            tendermint.rpc.grpc.Types.ResponseBroadcastTx.getDefaultInstance()))
                                    .build();
                }
            }
        }
        return getBroadcastTxMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static BroadcastAPIStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<BroadcastAPIStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<BroadcastAPIStub>() {
                    @java.lang.Override
                    public BroadcastAPIStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new BroadcastAPIStub(channel, callOptions);
                    }
                };
        return BroadcastAPIStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static BroadcastAPIBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<BroadcastAPIBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<BroadcastAPIBlockingStub>() {
                    @java.lang.Override
                    public BroadcastAPIBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new BroadcastAPIBlockingStub(channel, callOptions);
                    }
                };
        return BroadcastAPIBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static BroadcastAPIFutureStub newFutureStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<BroadcastAPIFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<BroadcastAPIFutureStub>() {
                    @java.lang.Override
                    public BroadcastAPIFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new BroadcastAPIFutureStub(channel, callOptions);
                    }
                };
        return BroadcastAPIFutureStub.newStub(factory, channel);
    }

    /**
     */
    public static abstract class BroadcastAPIImplBase implements io.grpc.BindableService {

        /**
         */
        public void ping(tendermint.rpc.grpc.Types.RequestPing request,
                         io.grpc.stub.StreamObserver<tendermint.rpc.grpc.Types.ResponsePing> responseObserver) {
            asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
        }

        /**
         */
        public void broadcastTx(tendermint.rpc.grpc.Types.RequestBroadcastTx request,
                                io.grpc.stub.StreamObserver<tendermint.rpc.grpc.Types.ResponseBroadcastTx> responseObserver) {
            asyncUnimplementedUnaryCall(getBroadcastTxMethod(), responseObserver);
        }

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getPingMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            tendermint.rpc.grpc.Types.RequestPing,
                                            tendermint.rpc.grpc.Types.ResponsePing>(
                                            this, METHODID_PING)))
                    .addMethod(
                            getBroadcastTxMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            tendermint.rpc.grpc.Types.RequestBroadcastTx,
                                            tendermint.rpc.grpc.Types.ResponseBroadcastTx>(
                                            this, METHODID_BROADCAST_TX)))
                    .build();
        }
    }

    /**
     */
    public static final class BroadcastAPIStub extends io.grpc.stub.AbstractAsyncStub<BroadcastAPIStub> {
        private BroadcastAPIStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected BroadcastAPIStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new BroadcastAPIStub(channel, callOptions);
        }

        /**
         */
        public void ping(tendermint.rpc.grpc.Types.RequestPing request,
                         io.grpc.stub.StreamObserver<tendermint.rpc.grpc.Types.ResponsePing> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void broadcastTx(tendermint.rpc.grpc.Types.RequestBroadcastTx request,
                                io.grpc.stub.StreamObserver<tendermint.rpc.grpc.Types.ResponseBroadcastTx> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getBroadcastTxMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     */
    public static final class BroadcastAPIBlockingStub extends io.grpc.stub.AbstractBlockingStub<BroadcastAPIBlockingStub> {
        private BroadcastAPIBlockingStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected BroadcastAPIBlockingStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new BroadcastAPIBlockingStub(channel, callOptions);
        }

        /**
         */
        public tendermint.rpc.grpc.Types.ResponsePing ping(tendermint.rpc.grpc.Types.RequestPing request) {
            return blockingUnaryCall(
                    getChannel(), getPingMethod(), getCallOptions(), request);
        }

        /**
         */
        public tendermint.rpc.grpc.Types.ResponseBroadcastTx broadcastTx(tendermint.rpc.grpc.Types.RequestBroadcastTx request) {
            return blockingUnaryCall(
                    getChannel(), getBroadcastTxMethod(), getCallOptions(), request);
        }
    }

    /**
     */
    public static final class BroadcastAPIFutureStub extends io.grpc.stub.AbstractFutureStub<BroadcastAPIFutureStub> {
        private BroadcastAPIFutureStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected BroadcastAPIFutureStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new BroadcastAPIFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<tendermint.rpc.grpc.Types.ResponsePing> ping(
                tendermint.rpc.grpc.Types.RequestPing request) {
            return futureUnaryCall(
                    getChannel().newCall(getPingMethod(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<tendermint.rpc.grpc.Types.ResponseBroadcastTx> broadcastTx(
                tendermint.rpc.grpc.Types.RequestBroadcastTx request) {
            return futureUnaryCall(
                    getChannel().newCall(getBroadcastTxMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_PING = 0;
    private static final int METHODID_BROADCAST_TX = 1;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final BroadcastAPIImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(BroadcastAPIImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_PING:
                    serviceImpl.ping((tendermint.rpc.grpc.Types.RequestPing) request,
                            (io.grpc.stub.StreamObserver<tendermint.rpc.grpc.Types.ResponsePing>) responseObserver);
                    break;
                case METHODID_BROADCAST_TX:
                    serviceImpl.broadcastTx((tendermint.rpc.grpc.Types.RequestBroadcastTx) request,
                            (io.grpc.stub.StreamObserver<tendermint.rpc.grpc.Types.ResponseBroadcastTx>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (BroadcastAPIGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .addMethod(getPingMethod())
                            .addMethod(getBroadcastTxMethod())
                            .build();
                }
            }
        }
        return result;
    }
}

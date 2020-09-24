package iservice.sdk;

import com.google.protobuf.ByteString;
import irismod.service.QueryGrpc;
import irismod.service.QueryOuterClass;
import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.net.GrpcChannel;
import org.bouncycastle.util.encoders.Hex;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Yelong
 */
public class ServiceTest {
    public static void main(String[] args) throws URISyntaxException {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setGrpcURI(new URI("http://10.1.4.150:9090"));
        ServiceClient client = ServiceClientFactory.getInstance().setOptions(options).getClient();
        QueryGrpc.QueryBlockingStub queryBlockingStub = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
        byte[] decode = Hex.decode("B68B0012D7EA88D904470105EFA506A6341FBC2245B7F81FFC95DC1CDB2445CE0000000000000000000000000000002B00000000000025340000");
        String s = new String(decode);
        System.out.println(decode);
        QueryOuterClass.QueryRequestResponse request = queryBlockingStub.request(QueryOuterClass.QueryRequestRequest.newBuilder().setRequestId(ByteString.copyFrom(decode)).build());
        request.getRequest().getInput();
        System.out.println(request.getRequest());
    }
}

package iservice.sdk;

import com.alibaba.fastjson.JSON;
import iservice.sdk.entity.Res;
import iservice.sdk.exception.ServiceSDKException;

import java.util.Objects;

import static junit.framework.TestCase.assertNotNull;

public class ParseObjectTest {
    public static void main(String[] args) {
        String res = "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"id\": \"service_client\",\n" +
                "  \"result\": {\n" +
                "    \"check_tx\": {\n" +
                "      \"code\": 0,\n" +
                "      \"data\": \"\",\n" +
                "      \"log\": \"[]\",\n" +
                "      \"info\": \"\",\n" +
                "      \"gas_wanted\": \"200000\",\n" +
                "      \"gas_used\": \"63931\",\n" +
                "      \"events\": [],\n" +
                "      \"codespace\": \"\"\n" +
                "    },\n" +
                "    \"deliver_tx\": {\n" +
                "      \"code\": 0,\n" +
                "      \"data\": \"CmIKDGNhbGxfc2VydmljZRJSClA0MDdBRkUxNTY0RUFBOTIxQ0ZBNzQ3Njk5NTcwNDAyM0ZFMUZFM0QzRERFQUM0MjY4RjdERkMzQzFENjNCMTA3MDAwMDAwMDAwMDAwMDAwMA==\",\n" +
                "      \"log\": \"[{\\\"events\\\":[{\\\"type\\\":\\\"create_context\\\",\\\"attributes\\\":[{\\\"key\\\":\\\"request_context_id\\\",\\\"value\\\":\\\"407AFE1564EAA921CFA7476995704023FE1FE3D3DDEAC4268F7DFC3C1D63B1070000000000000000\\\"},{\\\"key\\\":\\\"service_name\\\",\\\"value\\\":\\\"update\\\"},{\\\"key\\\":\\\"consumer\\\",\\\"value\\\":\\\"iaa1amrajvcq4j7m0vrr58f5rtaq6mupap98htr3hm\\\"}]},{\\\"type\\\":\\\"message\\\",\\\"attributes\\\":[{\\\"key\\\":\\\"action\\\",\\\"value\\\":\\\"call_service\\\"},{\\\"key\\\":\\\"module\\\",\\\"value\\\":\\\"service\\\"},{\\\"key\\\":\\\"sender\\\",\\\"value\\\":\\\"iaa1amrajvcq4j7m0vrr58f5rtaq6mupap98htr3hm\\\"}]}]}]\",\n" +
                "      \"info\": \"\",\n" +
                "      \"gas_wanted\": \"200000\",\n" +
                "      \"gas_used\": \"85090\",\n" +
                "      \"events\": [\n" +
                "        {\n" +
                "          \"type\": \"transfer\",\n" +
                "          \"attributes\": [\n" +
                "            {\n" +
                "              \"key\": \"cmVjaXBpZW50\",\n" +
                "              \"value\": \"aWFhMTd4cGZ2YWttMmFtZzk2MnlsczZmODR6M2tlbGw4YzVsOW1yM2Z2\",\n" +
                "              \"index\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"key\": \"c2VuZGVy\",\n" +
                "              \"value\": \"aWFhMWFtcmFqdmNxNGo3bTB2cnI1OGY1cnRhcTZtdXBhcDk4aHRyM2ht\",\n" +
                "              \"index\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"key\": \"YW1vdW50\",\n" +
                "              \"value\": \"NHN0YWtl\",\n" +
                "              \"index\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"message\",\n" +
                "          \"attributes\": [\n" +
                "            {\n" +
                "              \"key\": \"c2VuZGVy\",\n" +
                "              \"value\": \"aWFhMWFtcmFqdmNxNGo3bTB2cnI1OGY1cnRhcTZtdXBhcDk4aHRyM2ht\",\n" +
                "              \"index\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"message\",\n" +
                "          \"attributes\": [\n" +
                "            {\n" +
                "              \"key\": \"YWN0aW9u\",\n" +
                "              \"value\": \"Y2FsbF9zZXJ2aWNl\",\n" +
                "              \"index\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"create_context\",\n" +
                "          \"attributes\": [\n" +
                "            {\n" +
                "              \"key\": \"cmVxdWVzdF9jb250ZXh0X2lk\",\n" +
                "              \"value\": \"NDA3QUZFMTU2NEVBQTkyMUNGQTc0NzY5OTU3MDQwMjNGRTFGRTNEM0RERUFDNDI2OEY3REZDM0MxRDYzQjEwNzAwMDAwMDAwMDAwMDAwMDA=\",\n" +
                "              \"index\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"key\": \"c2VydmljZV9uYW1l\",\n" +
                "              \"value\": \"dXBkYXRl\",\n" +
                "              \"index\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"key\": \"Y29uc3VtZXI=\",\n" +
                "              \"value\": \"aWFhMWFtcmFqdmNxNGo3bTB2cnI1OGY1cnRhcTZtdXBhcDk4aHRyM2ht\",\n" +
                "              \"index\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"message\",\n" +
                "          \"attributes\": [\n" +
                "            {\n" +
                "              \"key\": \"bW9kdWxl\",\n" +
                "              \"value\": \"c2VydmljZQ==\",\n" +
                "              \"index\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"key\": \"c2VuZGVy\",\n" +
                "              \"value\": \"aWFhMWFtcmFqdmNxNGo3bTB2cnI1OGY1cnRhcTZtdXBhcDk4aHRyM2ht\",\n" +
                "              \"index\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"codespace\": \"\"\n" +
                "    },\n" +
                "    \"hash\": \"407AFE1564EAA921CFA7476995704023FE1FE3D3DDEAC4268F7DFC3C1D63B107\",\n" +
                "    \"height\": \"4387\"\n" +
                "  }\n" +
                "}";

        Res result = JSON.parseObject(res, Res.class);
        if (result.getResult().getDeliverTx().getCode() != 0) {
            System.out.println("Failed to call service!");
            throw new ServiceSDKException();
        }

        String reqCxtID = "";
        Res.Result.DeliverTx deliverTx = result.getResult().getDeliverTx();
        for (int i = 0; i < deliverTx.getEvents().length; i++) {
            if (Objects.equals(deliverTx.getEvents()[i].getType(), "create_context")) {
                reqCxtID = deliverTx.getEvents()[i].getAttributes()[0].getValue();
                break;
            }
        }

        System.out.println(reqCxtID);
        assertNotNull(reqCxtID);
    }
}

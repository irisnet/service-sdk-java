package irita.sdk.module.wasm;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

// for lcd query
public class ContractQuery {
    public static String build(String method, Map<String, Object> args) {
        String arg = JSON.toJSONString(args);
        if ("null".equals(arg)) {
            arg = "{}";
        }

        String builder = "{" +
                "\"" +
                method +
                "\":" +
                arg +
                "}";

        byte[] encode = Base64.getEncoder().encode(builder.getBytes(StandardCharsets.UTF_8));
        return new String(encode, StandardCharsets.UTF_8);
    }
}

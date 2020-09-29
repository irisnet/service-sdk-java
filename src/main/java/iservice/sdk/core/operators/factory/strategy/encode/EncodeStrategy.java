package iservice.sdk.core.operators.factory.strategy.encode;

import java.io.IOException;

/**
 * @author : ori
 * @date : 2020/9/28 5:45 下午
 */
public abstract class EncodeStrategy {

    public abstract byte[] encode(byte[] bytes) throws IOException;

    public abstract byte[] decode(byte[] b) throws Exception;

}

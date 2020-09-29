package iservice.sdk.core.operators.factory.strategy.encode;

import iservice.sdk.util.GzipUtils;

import java.io.IOException;

/**
 * @author : ori
 * @date : 2020/9/28 6:43 下午
 */
public class GzipEncodeStrategy extends EncodeStrategy {
    @Override
    public byte[] encode(byte[] bytes) throws IOException {
        return GzipUtils.gzip(bytes);
    }

    @Override
    public byte[] decode(byte[] bytes) throws Exception {
        return GzipUtils.unGzip(bytes);
    }
}

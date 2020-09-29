package iservice.sdk.core.operators.factory;

import iservice.sdk.core.operators.factory.strategy.encode.EncodeStrategy;
import iservice.sdk.core.operators.factory.strategy.encode.GzipEncodeStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ori
 * @date : 2020/9/28 5:39 下午
 */
public class EncodeStrategyFactory {

    private EncodeStrategyFactory(){
        strategyMap.put(EncodeType.GZIP.getName(), new GzipEncodeStrategy());
    }

    private static class InstanceHolder {
        private static final EncodeStrategyFactory INSTANCE = new EncodeStrategyFactory();
    }

    public static EncodeStrategyFactory getInstance(){
        return InstanceHolder.INSTANCE;
    }

    private final Map<String, EncodeStrategy> strategyMap = new HashMap<>();

    public EncodeStrategy getEncodeStrategy(String encodeStrategyName){
        return strategyMap.get(encodeStrategyName);
    }

    enum  EncodeType {
        GZIP("gzip"),
        COMPRESS("compress")
        ;
        private final String name;

        EncodeType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

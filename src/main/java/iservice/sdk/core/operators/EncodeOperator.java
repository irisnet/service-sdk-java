package iservice.sdk.core.operators;

import iservice.sdk.core.operators.factory.EncodeStrategyFactory;
import iservice.sdk.core.operators.factory.strategy.encode.EncodeStrategy;
import iservice.sdk.entity.Header;
import iservice.sdk.entity.ctx.DataChainContext;

import java.io.IOException;
import java.util.Base64;

/**
 * @author : ori
 * @date : 2020/9/28 5:15 下午
 */
public class EncodeOperator extends BaseDataOperator{

    EncodeOperator(BaseDataOperator next) {
        super(next);
    }

    @Override
    public void doSend(DataChainContext context) {
        EncodeStrategy strategy = getEncodeStrategy(context);
        try {
            context.setObj(strategy.encode(context.getObj()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(next!= null) {
            next.doSend(context);
        }
    }

    @Override
    public void doReceive(DataChainContext context) {
        // make next doReceive first
        if (next!= null) {
            next.doReceive(context);
        }
        EncodeStrategy strategy = getEncodeStrategy(context);
        try {
            context.setObj(strategy.decode(context.getObj()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EncodeStrategy getEncodeStrategy(DataChainContext context) {
        return EncodeStrategyFactory.getInstance().getEncodeStrategy(context.getHeader().getEncoding());
    }

    public static void main(String[] args) {
        DataChainContext ctx = new DataChainContext();
        Header header = new Header();
        header.setEncoding("gzip");
        ctx.setHeader(header);
        ctx.setObj("asdfaf".getBytes());
        EncodeOperator operator = new EncodeOperator(null);
        operator.doSend(ctx);
        String s = new String(Base64.getEncoder().encode(ctx.getObj()));
        System.out.println(s);
        ctx.setObj(Base64.getDecoder().decode(s));
        operator.doReceive(ctx);
        System.out.println(new String(ctx.getObj()));
    }
}

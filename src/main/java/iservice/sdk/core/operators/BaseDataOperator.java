package iservice.sdk.core.operators;

import iservice.sdk.entity.ctx.DataChainContext;

/**
 * @author : ori
 * @date : 2020/9/28 4:51 下午
 */
public abstract class BaseDataOperator {

    BaseDataOperator next;

    BaseDataOperator(BaseDataOperator next) {
        this.next = next;
    }

    /**
     * operation to do when sending message
     *
     * @param context
     */
    public abstract void doSend(DataChainContext context);

    /**
     * operation to do when receiving message
     *
     * @param context
     * @return
     */
    public abstract void doReceive(DataChainContext context);
}

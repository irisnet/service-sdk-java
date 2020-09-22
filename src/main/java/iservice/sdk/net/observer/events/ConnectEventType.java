package iservice.sdk.net.observer.events;

/**
 *
 *
 * @author : ori
 * @date : 2020/9/21 2:58 下午
 */
public enum ConnectEventType {
    /**
     * connect open
     */
    ON_OPEN,
    /**
     * connect receive message
     */
    ON_MESSAGE,
    /**
     * connect closed
     */
    ON_CLOSE,
    /**
     * connect error
     */
    ON_ERROR,
    ;
}

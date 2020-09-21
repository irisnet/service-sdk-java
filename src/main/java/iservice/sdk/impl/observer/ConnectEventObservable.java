package iservice.sdk.impl.observer;

import java.util.Observable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/9/21 5:22 下午
 */
public class ConnectEventObservable extends Observable {
    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }
}

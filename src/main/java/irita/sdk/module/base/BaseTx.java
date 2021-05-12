package irita.sdk.module.base;

import irita.sdk.client.IritaClientOption;

public class BaseTx {
    private int gas;
    private IritaClientOption.Fee fee;

    public BaseTx(int gas, IritaClientOption.Fee fee) {
        this.gas = gas;
        this.fee = fee;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public IritaClientOption.Fee getFee() {
        return fee;
    }

    public void setFee(IritaClientOption.Fee fee) {
        this.fee = fee;
    }
}

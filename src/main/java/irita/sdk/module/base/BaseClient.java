package irita.sdk.module.base;

import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;

public class BaseClient extends Client {
    private IritaClientOption option;

    public BaseClient(Client client) {
        this.nodeUri = client.getNodeUri();
        this.lcd = client.getLcd();
        this.chainId = client.getChainId();
        this.opbOption = client.getOpbOption();
        this.option = client.getOption();
    }

    public Account queryAccount(String addr) {
        return super.queryAccount(addr);
    }

    public IritaClientOption getOption() {
        return option;
    }

    public void setOption(IritaClientOption option) {
        this.option = option;
    }
}

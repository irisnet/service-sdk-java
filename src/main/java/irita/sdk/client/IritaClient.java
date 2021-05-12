package irita.sdk.client;

import irita.opb.OpbOption;
import irita.sdk.module.bank.BankClient;
import irita.sdk.module.base.BaseClient;
import irita.sdk.module.community_gov.CommunityGovClient;
import irita.sdk.module.service.ServiceClient;
import irita.sdk.module.wasm.WasmClient;

public class IritaClient extends Client {
    private BaseClient baseClient;
    private BankClient bankClient;
    private WasmClient wasmClient;
    private ServiceClient serviceClient;
    private CommunityGovClient communityGovClient;

    public IritaClient(String nodeUri, String lcd, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.lcd = lcd;
        this.chainId = chainId;
        this.opbOption = OpbOption.disabled();
        this.option = option;
    }

    public IritaClient(String chainId, OpbOption opbOption, IritaClientOption option) {
        this.chainId = chainId;
        this.opbOption = opbOption;
        this.option = option;
    }

    public String getNodeUri() {
        return nodeUri;
    }

    public void setNodeUri(String nodeUri) {
        this.nodeUri = nodeUri;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public IritaClientOption getOption() {
        return option;
    }

    public void setOption(IritaClientOption option) {
        this.option = option;
    }

    public BankClient getBankClient() {
        if (this.bankClient == null) {
            this.bankClient = new BankClient(this);
        }

        return this.bankClient;
    }

    public BaseClient getBaseClient() {
        if (this.baseClient == null) {
            this.baseClient = new BaseClient(this);
        }

        return this.baseClient;
    }

    public WasmClient getWasmClient() {
        if (this.wasmClient == null) {
            this.wasmClient = new WasmClient(this);
        }

        return this.wasmClient;
    }

    public ServiceClient getServiceClient() {
        if (this.serviceClient == null) {
            this.serviceClient = new ServiceClient(this);
        }

        return this.serviceClient;
    }


    public CommunityGovClient getCommunityGovClient() {
        if (this.communityGovClient == null) {
            this.communityGovClient = new CommunityGovClient(this.getWasmClient());
        }

        return this.communityGovClient;
    }
}

package irita.sdk.model;

import irita.sdk.module.wasm.ContractInfo;

public class QueryContractInfoResp {
    private String address;
    private ContractInfo contractInfo;
    private int code;
    private String message;

    public boolean found() {
        return code == 0;
    }

    public boolean notFound() {
        return !found();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

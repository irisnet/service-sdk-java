package irita.sdk.module.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Txs {
    private String height;
    private String txhash;
    private String data;
    @JSONField(name = "raw_log")
    private String rawLog;
    private List<Logs> logs;
    @JSONField(name = "gas_wanted")
    private String gasWanted;
    @JSONField(name = "gas_used")
    private String gasUsed;
    private Tx tx;
    private Long timestamp;

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public String getTxhash() {
        return txhash;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setRawLog(String rawLog) {
        this.rawLog = rawLog;
    }

    public String getRawLog() {
        return rawLog;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setGasWanted(String gasWanted) {
        this.gasWanted = gasWanted;
    }

    public String getGasWanted() {
        return gasWanted;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setTx(Tx tx) {
        this.tx = tx;
    }

    public Tx getTx() {
        return tx;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
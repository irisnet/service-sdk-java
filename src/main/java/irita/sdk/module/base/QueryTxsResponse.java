package irita.sdk.module.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class QueryTxsResponse {
    @JSONField(name = "total_count")
    private String totalCount;
    private String count;
    @JSONField(name = "page_number")
    private String pageNumber;
    @JSONField(name = "page_total")
    private String pageTotal;
    private String limit;
    private List<Txs> txs;

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLimit() {
        return limit;
    }

    public void setTxs(List<Txs> txs) {
        this.txs = txs;
    }

    public List<Txs> getTxs() {
        return txs;
    }
}
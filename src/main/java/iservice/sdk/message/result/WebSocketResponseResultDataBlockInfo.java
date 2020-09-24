package iservice.sdk.message.result;

/**
 * @author : ori
 * @date : 2020/9/24 12:06 下午
 */
public class WebSocketResponseResultDataBlockInfo {
    private ResultEndBlock result_end_block;

    public ResultEndBlock getResult_end_block() {
        return result_end_block;
    }

    public void setResult_end_block(ResultEndBlock result_end_block) {
        this.result_end_block = result_end_block;
    }

    @Override
    public String toString() {
        return "WebSocketResponseResultDataBlockInfo{" +
                "result_end_block=" + result_end_block +
                '}';
    }
}

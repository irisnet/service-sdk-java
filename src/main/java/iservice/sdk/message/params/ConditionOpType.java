package iservice.sdk.message.params;

/**
 * @author : ori
 * @date : 2020/9/23 6:23 下午
 */
public enum ConditionOpType {
    /**
     * equals
     */
    EQUALS("="),
    /**
     * contains
     */
    CONTAINS("CONTAINS"),
    ;
    private String op;

    ConditionOpType(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }
}

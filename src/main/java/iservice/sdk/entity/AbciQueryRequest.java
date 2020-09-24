package iservice.sdk.entity;

/**
 * @author Yelong
 */
public class AbciQueryRequest {

    /**
     * Querier path
     */
    String path;
    /**
     * Input params
     */
    String data;
    /**
     * Use a specific height to query state at (this can error if the node is pruning state)
     */
    long height;
    // TODO
    boolean prove;

    public AbciQueryRequest() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public boolean isProve() {
        return prove;
    }

    public void setProve(boolean prove) {
        this.prove = prove;
    }
}
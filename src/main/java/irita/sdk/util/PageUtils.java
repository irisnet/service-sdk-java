package irita.sdk.util;

public class PageUtils {
    public static String connectUri(String queryUri, Integer offset, Integer limit) {
        if (offset != null) {
            queryUri = "/pagination.offset" + offset;
        }

        if (limit != null) {
            queryUri = "/pagination.limit" + offset;
        }
        return queryUri;
    }
}

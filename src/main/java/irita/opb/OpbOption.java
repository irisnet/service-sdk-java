package irita.opb;

public class OpbOption {
    private String opbUri;
    private String projectId;
    private String projectKey;
    private final OpbEnum opbEnum;

    private OpbOption(OpbEnum opbEnum) {
        this.opbEnum = opbEnum;
    }

    public OpbOption(String opbUri, String projectId, String projectKey) {
        this.opbUri = opbUri;
        this.projectId = projectId;
        this.projectKey = projectKey;
        this.opbEnum = OpbEnum.ENABLE;
    }

    public static OpbOption disabled() {
        return new OpbOption(OpbEnum.DISABLE);
    }

    public String getOpbRestUri() {
        if (opbEnum == OpbEnum.DISABLE) {
            return "";
        }

        return opbUri + "/api/" + projectId + "/rest";
    }

    public String getOpbRpcUri() {
        if (opbEnum == OpbEnum.DISABLE) {
            return "";
        }

        return opbUri + "/api/" + projectId + "/rpc";
    }

    public String getOpbUri() {
        return opbUri;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public OpbEnum getOpbEnum() {
        return opbEnum;
    }
}

package irita.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;

public class GetHashResp {
    private int code;
    private String message;
    private Data data;

    public static class Data {
        @JSONField(name = "department_name")
        String departmentName;
        @JSONField(name = "doc_type")
        Byte docType;

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public void setDocType(Byte docType) {
            this.docType = docType;
        }
    }

    public boolean found() {
        return code == 0;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

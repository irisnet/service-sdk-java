package iservice.sdk;

import iservice.sdk.entity.BaseServiceRequest;

/**
 * Created by mitch on 2020/9/16.
 */
public class TestRequest extends BaseServiceRequest {
    private String s1;
    private String s2;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    @Override
    public String toString() {
        return "TestRequest{" +
                "s1='" + s1 + '\'' +
                ", s2='" + s2 + '\'' +
                '}';
    }
}

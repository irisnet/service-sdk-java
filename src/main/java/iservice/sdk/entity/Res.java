package iservice.sdk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Res {
    private Result result;

    @Override
    public String toString() {
        return "Res{" +
                "result=" + result +
                '}';
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public static class Result {
        @JSONField(name = "deliver_tx")
        @SerializedName("deliver_tx")
        private DeliverTx deliverTx;
        private String hash;
        private int height;

        @Override
        public String toString() {
            return "Result{" +
                    "deliver_tx=" + deliverTx +
                    ", hash='" + hash + '\'' +
                    ", height=" + height +
                    '}';
        }

        public void setDeliverTx(DeliverTx DeliverTx) {
            this.deliverTx = DeliverTx;
        }

        public DeliverTx getDeliverTx() {
            return deliverTx;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getHash() {
            return hash;
        }

        public static class DeliverTx {
            private int code;
            private StringEvent[] events;

            @Override
            public String toString() {
                return "DeliverTx{" +
                        "code=" + code +
                        ", events=" + Arrays.toString(events) +
                        '}';
            }

            public void setCode(int code) {
                this.code = code;
            }

            public int getCode() {
                return code;
            }

            public void setEvents(StringEvent[] events) {
                this.events = events;
            }

            public StringEvent[] getEvents() {
                return events;
            }

            public static class StringEvent {
                private String type;
                private Attributes[] attributes;

                @Override
                public String toString() {
                    return "StringEvent{" +
                            "type='" + type + '\'' +
                            ", attributes=" + Arrays.toString(attributes) +
                            '}';
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getType() {
                    return type;
                }

                public void setAttributes(Attributes[] attributes) {
                    this.attributes = attributes;
                }

                public Attributes[] getAttributes() {
                    return attributes;
                }

                public static class Attributes {
                    private String key;
                    private String value;

                    @Override
                    public String toString() {
                        return "Attributes{" +
                                "key='" + key + '\'' +
                                ", value='" + value + '\'' +
                                '}';
                    }

                    public void setKey(String key) {
                        this.key = key;
                    }

                    public String getKey() {
                        return key;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    public String getValue() {
                        return value;
                    }
                }
            }
        }
    }
}

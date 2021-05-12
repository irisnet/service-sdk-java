package irita.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryAccountResp {
    private int height;
    private Result result;

    public static class Result {
        String type;
        Value value;

        public static class Value {
            String address;
            @JSONField(name = "account_number")
            int accountNumber;
            int sequence;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Integer getAccountNumber() {
                return accountNumber;
            }

            public void setAccountNumber(Integer accountNumber) {
                this.accountNumber = accountNumber;
            }

            public void setAccountNumber(int accountNumber) {
                this.accountNumber = accountNumber;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }

    public boolean found() {
        return height != 0;
    }

    public boolean notFound() {
        return !found();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getAddress() {
        return result.value.address;
    }

    public long getAccountNumber() {
        return result.value.accountNumber;
    }

    public long getSequence(){
        return result.value.sequence;
    }
}

package irita.sdk.model;

import java.util.List;

public class QueryContractStateResp {
    private List<Models> models ;

    public static class Models {
        private String key;

        private String value;

        public void setKey(String key){
            this.key = key;
        }
        public String getKey(){
            return this.key;
        }
        public void setValue(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }

    public List<Models> getModels() {
        return models;
    }

    public void setModels(List<Models> models) {
        this.models = models;
    }
}

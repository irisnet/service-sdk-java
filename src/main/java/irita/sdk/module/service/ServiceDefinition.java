package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ServiceDefinition {
    private String name;
    private String description;
    private List<String> tags;
    private String author;
    @JSONField(name = "author_description")
    private String authorDescription;
    private String schemas;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setSchemas(String schemas) {
        this.schemas = schemas;
    }

    public String getSchemas() {
        return schemas;
    }
}

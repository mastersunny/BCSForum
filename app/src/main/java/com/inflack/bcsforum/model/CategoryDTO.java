package com.inflack.bcsforum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orm.SugarRecord;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO extends SugarRecord implements Serializable {

    public static final String TAG = "categorydto";

    @JsonProperty("id")
    private int categoryId;

    @JsonProperty("name")
    private String name;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}

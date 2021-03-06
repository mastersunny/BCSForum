package com.inflack.bcsforum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse implements Serializable {


    @JsonProperty("status")
    String status;

    @JsonProperty("user")
    List<MemberDTO> user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MemberDTO> getUser() {
        return user;
    }

    public void setUser(List<MemberDTO> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}

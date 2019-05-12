package com.inflack.bcsforum.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("status")
    String status;

    @SerializedName("user")
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

package com.inflack.bcsforum.model;

import java.io.Serializable;

public class MemberDTO implements Serializable {

    private int id;
    private String name;
    private String idNo;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idNo='" + idNo + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}

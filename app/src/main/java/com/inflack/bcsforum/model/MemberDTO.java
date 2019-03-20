package com.inflack.bcsforum.model;

import java.io.Serializable;

public class MemberDTO implements Serializable {

    private int id;
    private String name;
    private String position;
    private String employment;
    private String idNo;
    private String currentEmployment;
    private String previousEmployment;
    private String district;
    private String dateOfBirth;
    private String phoneNo;
    private String email;
    private String bloodGroup;
    private String imgUrl;

    public MemberDTO() {
    }

    public MemberDTO(String name, String position, String employment, String idNo, String currentEmployment, String previousEmployment, String district, String dateOfBirth, String phoneNo, String email, String bloodGroup, String imgUrl) {
        this.name = name;
        this.position = position;
        this.employment = employment;
        this.idNo = idNo;
        this.currentEmployment = currentEmployment;
        this.previousEmployment = previousEmployment;
        this.district = district;
        this.dateOfBirth = dateOfBirth;
        this.phoneNo = phoneNo;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.imgUrl = imgUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getCurrentEmployment() {
        return currentEmployment;
    }

    public void setCurrentEmployment(String currentEmployment) {
        this.currentEmployment = currentEmployment;
    }

    public String getPreviousEmployment() {
        return previousEmployment;
    }

    public void setPreviousEmployment(String previousEmployment) {
        this.previousEmployment = previousEmployment;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

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

package com.inflack.bcsforum.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberDTO implements Serializable {

    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("designation")
    private String designation;
    @SerializedName("company")
    private String company;
    @SerializedName("id_no")
    private String idNo;
    @SerializedName("current_workplace")
    private String currentEmployment;
    @SerializedName("previous_workplace")
    private String previousEmployment;
    @SerializedName("birth_district")
    private String district;
    @SerializedName("dob")
    private String dateOfBirth;
    @SerializedName("cell_no")
    private String phoneNo;
    @SerializedName("email")
    private String email;
    @SerializedName("blood_group")
    private String bloodGroup;
    @SerializedName("profile_picture")
    private String imgUrl;

    public MemberDTO() {
    }

    public MemberDTO(String name, String designation, String company, String idNo, String currentEmployment, String previousEmployment, String district, String dateOfBirth, String phoneNo, String email, String bloodGroup, String imgUrl) {
        this.name = name;
        this.designation = designation;
        this.company = company;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
                ", designation='" + designation + '\'' +
                ", company='" + company + '\'' +
                ", idNo='" + idNo + '\'' +
                ", currentEmployment='" + currentEmployment + '\'' +
                ", previousEmployment='" + previousEmployment + '\'' +
                ", district='" + district + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}

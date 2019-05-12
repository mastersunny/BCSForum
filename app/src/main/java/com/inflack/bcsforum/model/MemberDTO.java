package com.inflack.bcsforum.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;


public class MemberDTO extends SugarRecord implements Serializable {

    public static final String TAG = "memberdto";

    @Expose
    @SerializedName("id")
    private int userId;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("designation")
    private String designation;
    @Expose
    @SerializedName("company")
    private String company;
    @Expose
    @SerializedName("id_no")
    private String idNo;
    @Expose
    @SerializedName("current_workplace")
    private String currentEmployment;
    @Expose
    @SerializedName("previous_workplace")
    private String previousEmployment;
    @Expose
    @SerializedName("birth_district")
    private String district;
    @Expose
    @SerializedName("dob")
    private String dateOfBirth;
    @Expose
    @SerializedName("cell_no")
    private String phoneNo;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("blood_group")
    private String bloodGroup;
    @Expose
    @SerializedName("profile_picture")
    private String profilePicture;

    public MemberDTO() {
    }

    public MemberDTO(String name, String designation, String company, String idNo, String currentEmployment, String previousEmployment, String district, String dateOfBirth, String phoneNo, String email, String bloodGroup, String profilePicture) {
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
        this.profilePicture = profilePicture;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "userId=" + userId +
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
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

    public static MemberDTO getMember() {
        List<MemberDTO> branches = MemberDTO.listAll(MemberDTO.class);
        if (branches != null && branches.size() > 0) {
            return branches.get(0);
        } else {
            return null;
        }
    }
}

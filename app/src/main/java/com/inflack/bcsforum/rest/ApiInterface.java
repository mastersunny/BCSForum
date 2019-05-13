package com.inflack.bcsforum.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.inflack.bcsforum.model.CommitteeDTO;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ASUS on 1/20/2018.
 */

public interface ApiInterface {

    @GET("api/contacts")
    Call<List<MemberDTO>> getMembers();

    @GET("api/committeenforums")
    Call<List<CommitteeDTO>> getCommitteeForums();

    @POST("api/myProfile")
    Call<UserResponse> login(@Query("id_no") String id_no,
                             @Query("password") String password);

    @POST("api/editProfile")
    Call<JsonNode> editProfile(@Body MemberDTO json);

    @GET("api/getProfile")
    Call<UserResponse> getProfile(@Query("id_no") String id_no);

    @POST("api/changePassword")
    Call<JsonNode> changePassword(@Query("id_no") String id_no,
                                  @Query("oldPassword") String oldPassword,
                                  @Query("newPassword") String newPassword);


}

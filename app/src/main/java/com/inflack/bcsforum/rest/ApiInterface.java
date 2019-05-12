package com.inflack.bcsforum.rest;

import com.google.gson.JsonObject;
import com.inflack.bcsforum.model.CommitteeDTO;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.UserResponse;

import java.util.List;

import retrofit2.Call;
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


}

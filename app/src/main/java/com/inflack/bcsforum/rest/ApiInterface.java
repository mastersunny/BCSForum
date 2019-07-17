package com.inflack.bcsforum.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.inflack.bcsforum.model.CategoryDTO;
import com.inflack.bcsforum.model.CommitteeDTO;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.NewsDTO;
import com.inflack.bcsforum.model.NotificationDTO;
import com.inflack.bcsforum.model.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ASUS on 1/20/2018.
 */

public interface ApiInterface {

    @GET("api/profiles")
    Call<List<MemberDTO>> getMembers(@Query("category_id") int categoryId);

    @GET("api/categories")
    Call<List<CategoryDTO>> getCategories();

    @GET("api/committeenforums")
    Call<List<CommitteeDTO>> getCommitteeForums();

    @POST("api/myProfile")
    Call<UserResponse> login(@Query("id_no") String id_no,
                             @Query("password") String password);

    @POST("api/editProfile")
    Call<JsonNode> editProfile(@Body MemberDTO json);

    @GET("api/getProfile")
    Call<UserResponse> getProfile(@Query("idNo") String idNo);

    @POST("api/changePassword")
    Call<JsonNode> changePassword(@Query("id_no") String id_no,
                                  @Query("oldPassword") String oldPassword,
                                  @Query("newPassword") String newPassword);

    @Multipart
    @POST("api/profileUpload")
    Call<JsonNode> updatePhoto(@Part MultipartBody.Part profile_picture, @Part("id") RequestBody id);


    @GET("api/settings")
    Call<List<NewsDTO>> getNewsUpdate();

    @GET("api/profileNotification")
    Call<List<NotificationDTO>> getNotifications(@Query("createdAt") Long createdAt);

}

package com.inflack.bcsforum.rest;

import com.inflack.bcsforum.model.CommitteeDTO;
import com.inflack.bcsforum.model.MemberDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ASUS on 1/20/2018.
 */

public interface ApiInterface {

    @GET("api/contacts")
    Call<List<MemberDTO>> getMembers();

    @GET("api/committeenforums")
    Call<List<CommitteeDTO>> getCommitteeForums();

}

package com.example.myapplication;


import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://pastebin.com/raw/";
    @GET("wgkJgazE")
    Call<List<PostList>> getProfiles();
}

package com.jeppung.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUsers(@Query("q") username: String): Call<ListUsersResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<ListUserFollowersResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowings(@Path("username") username: String): Call<List<ListUserFollowingsResponseItem>>
}
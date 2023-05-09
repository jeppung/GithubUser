package com.jeppung.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel() {

    private var _userDetailResponse = MutableLiveData<UserDetailResponse>()
    val userDetailResponse: LiveData<UserDetailResponse> = _userDetailResponse

    private var _setLoadingDetail = MutableLiveData<Boolean>()
    val setLoadingDetail: LiveData<Boolean> = _setLoadingDetail

    private var _setLoadingFollowers = MutableLiveData<Boolean>()
    val setLoadingFollowers: LiveData<Boolean> = _setLoadingFollowers

    private var _setLoadingFollowings = MutableLiveData<Boolean>()
    val setLoadingFollowings: LiveData<Boolean> = _setLoadingFollowings

    private var _errUserDetailMsg = MutableLiveData<Event<String>>()
    val errUserDetailMsg: LiveData<Event<String>> = _errUserDetailMsg

    private var _userFollowers = MutableLiveData<List<ListUserFollowersResponseItem>>()
    val userFollowers: LiveData<List<ListUserFollowersResponseItem>> = _userFollowers

    private var _userFollowings = MutableLiveData<List<ListUserFollowingsResponseItem>>()
    val userFollowings: LiveData<List<ListUserFollowingsResponseItem>> = _userFollowings

    fun getUserDetail(username: String) {
        _setLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object: Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _setLoadingDetail.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()

                    _userDetailResponse.value = responseBody!!
                }else{
                    _errUserDetailMsg.value = Event("error: API Rate Limiting")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _setLoadingDetail.value = false
                _errUserDetailMsg.value = Event("error: ${t.message} - getUserDetail")
            }

        })
    }

    fun getUserFollowers(username: String) {
        _setLoadingDetail.value = true

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object: Callback<List<ListUserFollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<ListUserFollowersResponseItem>>,
                response: Response<List<ListUserFollowersResponseItem>>
            ) {
                _setLoadingDetail.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    _userFollowers.value = responseBody!!
                }else{
                    _errUserDetailMsg.value = Event("error: API Rate Limiting")
                }
            }

            override fun onFailure(call: Call<List<ListUserFollowersResponseItem>>, t: Throwable) {
                _setLoadingDetail.value = false
                _errUserDetailMsg.value = Event("error: ${t.message}")
            }

        })
    }

    fun getUserFollowings(username: String) {
        _setLoadingDetail.value = true

        val client = ApiConfig.getApiService().getUserFollowings(username)
        client.enqueue(object: Callback<List<ListUserFollowingsResponseItem>> {
            override fun onResponse(
                call: Call<List<ListUserFollowingsResponseItem>>,
                response: Response<List<ListUserFollowingsResponseItem>>
            ) {
                _setLoadingDetail.value = false

                if(response.isSuccessful){
                    val responseBody = response.body()
                    _userFollowings.value = responseBody!!
                }else{
                    _errUserDetailMsg.value = Event("error: API Rate Limiting")
                }
            }

            override fun onFailure(call: Call<List<ListUserFollowingsResponseItem>>, t: Throwable) {
                _setLoadingDetail.value = false
                _errUserDetailMsg.value = Event("error: ${t.message}")
            }

        })
    }


}
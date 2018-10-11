package com.rpo.mobile.utils


import com.rpo.mobile.modal.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIinterface {
    /**
     * get user
     */
    @GET("/users")
    fun getValidateUser(): Call<List<User>>

    /**
     * Create user
     */
    @POST("/users")
    fun toCreateA_User(@Body user: com.rpo.mobile.modal.User): Call<User>


}
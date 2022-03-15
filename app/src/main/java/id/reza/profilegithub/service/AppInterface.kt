package id.reza.profilegithub.service

import id.reza.profilegithub.model.DetailUser
import id.reza.profilegithub.model.OneData
import id.reza.profilegithub.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppInterface {

    @GET("users")
    suspend fun getData(@Query("page") page: String, @Query("per_page") per_page: String): Response<List<OneData>>

    @GET("users/{username}")
    suspend fun getOneUser(@Path("username") username: String): Response<DetailUser>

}
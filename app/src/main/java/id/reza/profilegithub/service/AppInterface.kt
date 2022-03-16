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
    //fix wrong query
    //query to pagination not use 'page' anymore but used 'since'
    //where 'since' is the user ID that return users with an
    //ID greater than this ID (from api documentation)
    //https://docs.github.com/en/rest/reference/users
    suspend fun getData(@Query("since") page: String, @Query("per_page") per_page: String): Response<List<OneData>>

    @GET("users/{username}")
    suspend fun getOneUser(@Path("username") username: String): Response<DetailUser>

}
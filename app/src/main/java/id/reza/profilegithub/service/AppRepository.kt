package id.reza.profilegithub.service

import id.reza.profilegithub.model.DetailUser
import id.reza.profilegithub.model.OneData
import id.reza.profilegithub.model.User
import retrofit2.Response

class AppRepository(private val appInterface: AppInterface) {

    suspend fun getData(page: String, pageSize: String) : Response<List<OneData>> =
        appInterface.getData(page, pageSize)

    suspend fun getOneUser(username: String) : Response<DetailUser> =
        appInterface.getOneUser(username)
}
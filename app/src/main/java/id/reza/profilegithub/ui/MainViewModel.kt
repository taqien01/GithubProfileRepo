package id.reza.profilegithub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.reza.profilegithub.model.DetailUser
import id.reza.profilegithub.model.OneData
import id.reza.profilegithub.utils.SingleLiveEvent
import id.reza.profilegithub.model.User
import id.reza.profilegithub.service.AppRepository
import id.reza.profilegithub.service.NetworkConnectionInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.net.HttpURLConnection

class MainViewModel(private val appRepository: AppRepository, private val networkConnectionInterceptor: NetworkConnectionInterceptor): ViewModel() {

    val errorEvent = SingleLiveEvent<String>()
    val loadingEvent = SingleLiveEvent<Boolean>()
    val listEvent = SingleLiveEvent<List<OneData>>()
    val detailEvent = SingleLiveEvent<DetailUser>()
    val pageEvent = SingleLiveEvent<String>()

    fun getData(page: String){
        loadingEvent.value = true

        var response: Response<List<OneData>>

        if (!networkConnectionInterceptor.isInternetAvailable()){
            errorEvent.value = "No Internet Access"
            loadingEvent.value = false
        }else{
            viewModelScope.launch {
                with(Dispatchers.IO){
                    response = appRepository.getData(page, "10")
                }

                with(Dispatchers.Main){
                    try {
                        when (response.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                //set the page query by the last id from the list
                                pageEvent.value = response.body()!!.last().id.toString()

                                listEvent.value = response.body()
                            }
                            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                errorEvent.value = "Something went wrong : ${response.code()}"
                            }
                            HttpURLConnection.HTTP_FORBIDDEN -> {
                                errorEvent.value = "Something went wrong : ${response.code()}"
                            }
                            else -> {
                                errorEvent.value = "Something went wrong : ${response.code()}"
                            }
                        }
                    }catch (e: Exception){
                        errorEvent.value = e.localizedMessage
                    }finally {

                        loadingEvent.value = false
                    }
                }
            }

        }
    }

    fun getOneUser(username: String){
        loadingEvent.value = true

        var response: Response<DetailUser>

        if (!networkConnectionInterceptor.isInternetAvailable()){
            errorEvent.value = "No Internet Access"
            loadingEvent.value = false
        }else{
            viewModelScope.launch {
                with(Dispatchers.IO){
                    response = appRepository.getOneUser(username)
                }

                with(Dispatchers.Main){
                    try {
                        when (response.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                detailEvent.value = response.body()
                            }
                            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                errorEvent.value = "Something went wrong : ${response.code()}"
                            }
                            HttpURLConnection.HTTP_FORBIDDEN -> {
                                errorEvent.value = "Something went wrong : ${response.code()}"
                            }
                            else -> {
                                errorEvent.value = "Something went wrong : ${response.code()}"
                            }
                        }
                    }catch (e: Exception){
                        errorEvent.value = e.localizedMessage
                    }finally {
                        loadingEvent.value = false
                    }
                }
            }
        }
    }
}
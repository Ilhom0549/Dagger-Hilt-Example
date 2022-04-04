package uz.ilkhomkhuja.daggerhiltexample.networking

import retrofit2.Response
import retrofit2.http.GET
import uz.ilkhomkhuja.daggerhiltexample.models.GithubUser

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<GithubUser>>
}
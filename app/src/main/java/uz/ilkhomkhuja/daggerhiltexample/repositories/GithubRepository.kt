package uz.ilkhomkhuja.daggerhiltexample.repositories

import kotlinx.coroutines.flow.flow
import uz.ilkhomkhuja.daggerhiltexample.database.dao.UserDao
import uz.ilkhomkhuja.daggerhiltexample.database.entity.UserEntity
import uz.ilkhomkhuja.daggerhiltexample.networking.ApiService
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    suspend fun getUsers() = flow { emit(apiService.getUsers()) }

    suspend fun insertUsers(list: List<UserEntity>) = userDao.insertUsers(list)

    suspend fun getDbUsers() = userDao.getUsers()
}
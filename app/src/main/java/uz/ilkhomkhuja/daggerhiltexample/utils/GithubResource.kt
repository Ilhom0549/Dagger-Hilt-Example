package uz.ilkhomkhuja.daggerhiltexample.utils

import uz.ilkhomkhuja.daggerhiltexample.database.entity.UserEntity

sealed class GithubResource {

    object Loading : GithubResource()

    data class Success(val list: List<UserEntity>) : GithubResource()

    data class Error(val msg: String) : GithubResource()
}
package uz.ilkhomkhuja.daggerhiltexample.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.ilkhomkhuja.daggerhiltexample.database.entity.UserEntity
import uz.ilkhomkhuja.daggerhiltexample.repositories.GithubRepository
import uz.ilkhomkhuja.daggerhiltexample.utils.GithubResource
import uz.ilkhomkhuja.daggerhiltexample.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val stateFlow = MutableStateFlow<GithubResource>(GithubResource.Loading)

    init {
        fetchUsers()
    }

    fun getUsers(): StateFlow<GithubResource> {
        return stateFlow
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                stateFlow.value = GithubResource.Loading
                val flow = githubRepository.getUsers()
                flow.catch {
                    stateFlow.value = GithubResource.Error(it.message ?: "Error")
                }.collect {
                    if (it.isSuccessful) {
                        val list = ArrayList<UserEntity>()
                        it.body()?.forEach { githubUser ->
                            list.add(
                                UserEntity(
                                    githubUser.id,
                                    githubUser.login,
                                    githubUser.avatar_url
                                )
                            )
                        }
                        githubRepository.insertUsers(list)
                        stateFlow.value = GithubResource.Success(githubRepository.getDbUsers())
                    } else {
                        stateFlow.value = GithubResource.Error(it.errorBody()?.string() ?: "Error")
                    }
                }
            } else {
                if (githubRepository.getDbUsers().isNotEmpty()) {
                    stateFlow.value = GithubResource.Success(githubRepository.getDbUsers())
                } else {
                    stateFlow.value = GithubResource.Error("Internet Disconnected !!")
                }
            }
        }
    }
}
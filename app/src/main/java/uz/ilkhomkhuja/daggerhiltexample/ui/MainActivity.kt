package uz.ilkhomkhuja.daggerhiltexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import uz.ilkhomkhuja.daggerhiltexample.adapters.UserAdapter
import uz.ilkhomkhuja.daggerhiltexample.databinding.ActivityMainBinding
import uz.ilkhomkhuja.daggerhiltexample.utils.GithubResource
import uz.ilkhomkhuja.daggerhiltexample.viewmodels.GithubViewModel
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val githubViewModel: GithubViewModel by viewModels()
    private val tag = "MainActivity"
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        binding.rv.adapter = userAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                githubViewModel.getUsers().collect {
                    when (it) {
                        is GithubResource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rv.visibility = View.GONE
                        }
                        is GithubResource.Success -> {
                            Log.d(tag, "onCreate: ${it.list}")
                            binding.progressBar.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            userAdapter.submitList(it.list)
                        }
                        is GithubResource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@MainActivity, it.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
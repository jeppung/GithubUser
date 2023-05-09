package com.jeppung.githubuser


import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jeppung.githubuser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContentView(binding.root)

        binding.usersList.layoutManager = LinearLayoutManager(this)

        viewModel.itemsItem.observe(this, Observer { data ->
            setUserList(data)
        })

        viewModel.setLoading.observe(this, Observer { data ->
            setLoading(data)
        })

        viewModel.resultFound.observe(this, Observer { data ->
            setResultFound(data)
        })

        viewModel.errorMsg.observe(this, Observer { data ->
            data.getContentIfNotHandled()?.let {
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query!!)
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setUserList(data: List<ItemsItem>) {
        val listUsers = ArrayList<ItemsItem>()
        for (user in data) {
            listUsers.add(user)
        }

        binding.usersList.adapter = UsersAdapter(listUsers) { data ->
            val i = Intent(this@MainActivity, UserDetailActivity::class.java)
            i.putExtra(UserDetailActivity.LOGIN_KEY, data.login)

            val connection = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

            if(connection.activeNetworkInfo?.isConnected == true){
                startActivity(i)
            }else{
                Snackbar.make(
                    binding.root,
                    "No internet connection",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setLoading(value: Boolean) {
        if (value) {
            binding.progressView.visibility = View.VISIBLE
        } else {
            binding.progressView.visibility = View.GONE
        }
    }

    private fun setResultFound(data: Int) {
        binding.searchResult.text = String.format(resources.getString(R.string.search_status), data)
        binding.searchResult.visibility = View.VISIBLE
    }
}
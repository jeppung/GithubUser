package com.jeppung.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.jeppung.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    companion object {
        const val LOGIN_KEY = "LOGIN"

        private val TAB_TITLES = arrayOf(
            "Followers",
            "Following",
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]

        setContentView(binding.root)

        val username = intent.getStringExtra(LOGIN_KEY)

        viewModel.setLoadingDetail.observe(this, Observer { data ->
            setLoading(data)
        })

        viewModel.userDetailResponse.observe(this, Observer { data ->
            setUserDetail(data)
        })

        viewModel.errUserDetailMsg.observe(this, Observer { data ->
            data.getContentIfNotHandled()?.let {
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        binding.itemDetailViewPager.adapter = ViewPagerAdapter(this).apply {
            this.username = username!!
        }

        viewModel.getUserDetail(username!!)

        TabLayoutMediator(binding.itemDetailTabs, binding.itemDetailViewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

    }

    private fun setUserDetail(data: UserDetailResponse) {
        Glide.with(this).load(data.avatarUrl).circleCrop().into(binding.itemDetailImage)
        binding.itemDetailName.text = data.name
        binding.itemDetailUsername.text = data.login
        binding.itemDetailFollowersCount.text = data.followers.toString()
        binding.itemDetailFollowingCount.text = data.following.toString()
    }

    private fun setLoading(data: Boolean) {
        if (data) {
            binding.itemDetailProgressView.visibility = View.VISIBLE
        } else {
            binding.itemDetailProgressView.visibility = View.INVISIBLE
        }
    }
}
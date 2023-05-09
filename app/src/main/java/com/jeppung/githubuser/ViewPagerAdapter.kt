package com.jeppung.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    var username = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position) {
            0 -> {
                fragment = UserFollowersFragment()
                fragment.arguments = Bundle().apply {
                    putString(UserFollowersFragment.USERNAME, username)
                }
            }
            1 -> {
                fragment = UserFollowingsFragment()
                fragment.arguments = Bundle().apply {
                    putString(UserFollowingsFragment.USERNAME, username)
                }
            }
        }

        return fragment as Fragment
    }

}
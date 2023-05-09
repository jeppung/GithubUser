package com.jeppung.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeppung.githubuser.databinding.FragmentUserFollowingBinding

class UserFollowingsFragment : Fragment() {

    private lateinit var binding: FragmentUserFollowingBinding
    private lateinit var viewModel: UserDetailViewModel

    companion object {
        const val USERNAME = "USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(requireActivity())[UserDetailViewModel::class.java]

        viewModel.userFollowings.observe(requireActivity(), Observer { data ->
            setUserFollowings(data)
        })

        viewModel.getUserFollowings(arguments?.getString(USERNAME).toString())

        binding = FragmentUserFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userFollowingsList.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun setUserFollowings(data: List<ListUserFollowingsResponseItem>) {
        val followingsList = ArrayList<ListUserFollowingsResponseItem>()

        for(user in data){
            followingsList.add(user)
        }

        binding.userFollowingsList.adapter = UserFollowingsAdapter(followingsList)
    }
}
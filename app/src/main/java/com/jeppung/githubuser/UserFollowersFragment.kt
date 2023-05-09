package com.jeppung.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeppung.githubuser.databinding.FragmentUserFollowersBinding

class UserFollowersFragment : Fragment() {

    private lateinit var binding: FragmentUserFollowersBinding
    private lateinit var viewModel: UserDetailViewModel

    companion object {
        const val USERNAME = "USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity())[UserDetailViewModel::class.java]

        viewModel.userFollowers.observe(requireActivity(), Observer { data ->
            setUserFollowers(data)
        })

        viewModel.setLoadingFollowers.observe(requireActivity(), Observer {data ->
            setLoadingFollowers(data)
        })

        viewModel.getUserFollowers(arguments?.getString(USERNAME).toString())

        binding = FragmentUserFollowersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userFollowersList.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun setUserFollowers(data: List<ListUserFollowersResponseItem>) {
        val followersList = ArrayList<ListUserFollowersResponseItem>()

        for (user in data){
            followersList.add(user)
        }

        binding.userFollowersList.adapter = UserFollowersAdapter(followersList)
    }

    private fun setLoadingFollowers(data: Boolean) {
        if(data){
            binding.userFollowersProgressView.visibility = View.VISIBLE
        }else{
            binding.userFollowersProgressView.visibility = View.GONE
        }
    }

}
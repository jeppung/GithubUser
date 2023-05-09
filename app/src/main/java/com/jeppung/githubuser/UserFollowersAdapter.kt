package com.jeppung.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeppung.githubuser.databinding.UserItemBinding

class UserFollowersAdapter(private val followersList: ArrayList<ListUserFollowersResponseItem>) :
    RecyclerView.Adapter<UserFollowersAdapter.ViewHolder>() {

    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(followersList[position].avatarUrl).circleCrop()
            .into(holder.binding.itemImage)
        holder.binding.itemUsername.text = followersList[position].login
        holder.binding.itemUserid.text = String.format(
            holder.itemView.context.resources.getString(R.string.user_id),
            followersList[position].id
        )
    }

    override fun getItemCount(): Int = followersList.size


}
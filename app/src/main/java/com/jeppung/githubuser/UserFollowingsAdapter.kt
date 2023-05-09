package com.jeppung.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeppung.githubuser.databinding.UserItemBinding

class UserFollowingsAdapter(private val followingsList: ArrayList<ListUserFollowingsResponseItem>) :
    RecyclerView.Adapter<UserFollowingsAdapter.ViewHolder>() {

    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemUsername.text = followingsList[position].login
        holder.binding.itemUserid.text = String.format(
            holder.itemView.context.resources.getString(R.string.user_id),
            followingsList[position].id
        )
        Glide.with(holder.itemView.context).load(followingsList[position].avatarUrl).circleCrop()
            .into(holder.binding.itemImage)
    }

    override fun getItemCount(): Int = followingsList.size

}
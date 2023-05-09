package com.jeppung.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeppung.githubuser.databinding.UserItemBinding

class UsersAdapter(
    private val usersList: ArrayList<ItemsItem>,
    private val onClick: (ItemsItem) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemUsername.text = usersList[position].login
        holder.binding.itemUserid.text = String.format(
            holder.itemView.context.resources.getString(R.string.user_id),
            usersList[position].id
        )
        Glide.with(holder.itemView.context).load(usersList[position].avatarUrl)
            .circleCrop().into(holder.binding.itemImage)

        holder.binding.itemContainer.setOnClickListener {
            onClick(usersList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = usersList.size

}
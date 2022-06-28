package com.example.github.ui.fragments.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Repository
import com.example.domain.models.User
import com.example.github.databinding.ItemRepositoryBinding
import com.example.github.databinding.ItemUserBinding
import com.example.github.utils.dateTimetoText
import com.example.github.utils.loadCircular

class UsersList (private val onclick : (User) -> Unit): ListAdapter<User, UsersList.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.apply {
            user.avatarUrl?.let { imageView2.loadCircular(it) }
           textView14.text= user.login
            root.setOnClickListener {
                onclick(user)
            }
        }
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}
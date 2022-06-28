package com.example.github.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Repository
import com.example.github.databinding.ItemRepositoryBinding
import com.example.github.utils.dateTimetoText

class RepositoriesAdapter (private val onclick : (Repository) -> Unit): ListAdapter<Repository, RepositoriesAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        holder.binding.apply {
            textViewTitle.text = repo.name
            textViewDesc.text = repo.description
            textViewLanguage.text = if (repo.language != null) repo.language else "Unknown"
            textViewLastUpdated.text = "Last updated ${repo.updatedAt?.dateTimetoText()} ago"
            root.setOnClickListener {
                onclick(repo)
            }
        }
    }

    class ViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

}
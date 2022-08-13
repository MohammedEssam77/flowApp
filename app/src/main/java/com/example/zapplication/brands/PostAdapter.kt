package com.example.zapplication.brands

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zapplication.databinding.PostItemBinding
import com.example.zapplication.entities.Posts

class PostAdapter (val callback: PostItemClick) : ListAdapter<Posts, PostAdapter.PostViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame( oldItem: Posts,newItem: Posts): Boolean {
            return oldItem == newItem
        }
    }

    class PostViewHolder(val viewDataBinding: PostItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(listener: PostItemClick, news: Posts) {
            viewDataBinding.post= news
            viewDataBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostItemBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.viewDataBinding.also {
            holder.bind(callback, getItem(position))
        }
    }
}

class PostItemClick(val block: (Posts) -> Unit) {
    fun onClick(item: Posts) = block(item)
}
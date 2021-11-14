package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.entity.Book
import com.example.myapplication.databinding.BookItemBinding
import com.squareup.picasso.Picasso

class BookAdapter : ListAdapter<Book, BookAdapter.BookViewHolder>(NewsItemsDiffCallback()) {

    private lateinit var inflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder.from(inflater, parent)

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookViewHolder(
        private val binding: BookItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) =
            with(binding) {
                tvTitle.text = item.title
                tvAuthor.text = item.author
                Picasso.get()
                    .load(item.imageURL)
                    .fit()
                    .into(ivImage)
            }

        companion object {
            fun from(inflater: LayoutInflater, parent: ViewGroup): BookViewHolder {
                val binding = BookItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return BookViewHolder(binding)
            }
        }
    }
}

class NewsItemsDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem == newItem
}
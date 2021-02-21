package io.peanutapp.newsfeed.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.peanutapp.newsfeed.databinding.ItemFeedPostBinding
import io.peanutapp.newsfeed.model.News
import io.peanutapp.newsfeed.model.Post

class MainAdapter(
  val rowClick: (id: String) -> Unit,
) : RecyclerView.Adapter<NewsViewHolder>() {
  private val news: ArrayList<Post> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
    val binding = ItemFeedPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return NewsViewHolder(binding, rowClick)
  }

  override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    holder.bind(news[position])
  }

  override fun getItemCount(): Int = news.size

  fun setProducts(newNews: News) {
    with(news) {
      clear()
      addAll(newNews.posts)
    }
    notifyDataSetChanged()
  }
}

class NewsViewHolder(
  private val binder: ItemFeedPostBinding,
  private val rowClick: (id: String) -> Unit,
) : RecyclerView.ViewHolder(binder.root) {

  fun bind(post: Post) {
    with(binder) {
      newsPost = post
      root.setOnClickListener {
        rowClick(post.uid)
      }
    }
  }
}

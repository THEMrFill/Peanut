package io.peanutapp.newsfeed.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.peanutapp.newsfeed.model.Post

@Entity(tableName = "news")
data class NewsEntity(
  val author: String,
  val body: String,
  val title: String,
  @PrimaryKey
  val uid: String,
) {
  constructor(post: Post) : this(
    author = post.author,
    body = post.body,
    title = post.title,
    uid = post.uid,
  )

  fun toPost() = Post(
    author = author,
    body = body,
    title = title,
    uid = uid,
  )
}

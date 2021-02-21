package io.peanutapp.newsfeed.model

data class News(
    val paging: Paging = Paging(),
    val posts: List<Post>
)

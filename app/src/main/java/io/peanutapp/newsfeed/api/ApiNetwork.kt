package io.peanutapp.newsfeed.api

import io.peanutapp.newsfeed.model.News
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiNetwork {
  @GET("posts")
  fun getNews(): Deferred<News>

}

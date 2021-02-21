package io.peanutapp.newsfeed.repo

import androidx.lifecycle.MutableLiveData
import io.peanutapp.newsfeed.api.SingleLiveEvent
import io.peanutapp.newsfeed.api.UseCaseResult
import io.peanutapp.newsfeed.model.News
import io.peanutapp.newsfeed.model.Post

interface NewsRepository {
  suspend fun getNews(): UseCaseResult<News>
  suspend fun loadNews(liveData: MutableLiveData<News>, error: SingleLiveEvent<String>)
  suspend fun getPost(id: String, liveData: MutableLiveData<Post>, error: SingleLiveEvent<String>)
}

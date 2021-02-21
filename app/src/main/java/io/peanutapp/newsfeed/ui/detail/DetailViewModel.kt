package io.peanutapp.newsfeed.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.peanutapp.newsfeed.api.SingleLiveEvent
import io.peanutapp.newsfeed.model.Post
import io.peanutapp.newsfeed.repo.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(val newsRepository: NewsRepository)
  : ViewModel(), CoroutineScope {
  // Coroutine's background job
  private val job = Job()

  // Define default thread for Coroutine as Main and add job
  override val coroutineContext: CoroutineContext = Dispatchers.Main + job

  val post = MutableLiveData<Post>()
  val error = SingleLiveEvent<String>()

  fun getPost(id: String) {
    launch {
      newsRepository.getPost(id, post, error)
    }
  }
}

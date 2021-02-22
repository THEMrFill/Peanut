package io.peanutapp.newsfeed.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.peanutapp.newsfeed.api.ApiNetwork
import io.peanutapp.newsfeed.api.SingleLiveEvent
import io.peanutapp.newsfeed.api.UseCaseResult
import io.peanutapp.newsfeed.model.News
import io.peanutapp.newsfeed.model.Post
import io.peanutapp.newsfeed.storage.NewsDao
import io.peanutapp.newsfeed.storage.NewsEntity
import kotlinx.coroutines.*

class NewsRepositoryImpl(
  private val api: ApiNetwork,
  private val context: Context,
  private val newsDao: NewsDao
) : NewsRepository {
  override suspend fun getNews(): UseCaseResult<News> {
    return try {
      // had to remove this as the API wasn't returning on the device or emulator
      val result = api.getNews().await()
      // reading the file from Assets temporarily to show the app working
//      val fileName = "news.json"
//      val jsonString = context.assets.open(fileName).bufferedReader().use{
//        it.readText()
//      }
//      val result = Gson().fromJson<News>(jsonString, News::class.java)
      saveNews(result.posts)
      UseCaseResult.Success(result)
    } catch (ex: Exception) {
      UseCaseResult.Error(ex)
    }
  }

  override suspend fun loadNews(liveData: MutableLiveData<News>, error: SingleLiveEvent<String>) {
    GlobalScope.launch(Dispatchers.IO) {
      val loadedNews = getStoredNews()
      if (loadedNews.isNotEmpty()) {
        GlobalScope.launch(Dispatchers.Main) {
          liveData.value = News(posts = loadedNews)
        }
      }
      when (val result = withContext(Dispatchers.IO) { getNews() }) {
        is UseCaseResult.Success -> {
          GlobalScope.launch(Dispatchers.Main) {
            liveData.value = result.data
          }
        }
        is UseCaseResult.Error -> {
          GlobalScope.launch(Dispatchers.Main) {
            error.value = result.exception.message
          }
        }
      }
    }
  }

  suspend fun saveNews(news: List<Post>) {
    for (item in news) {
      newsDao.insert(NewsEntity(item))
    }
  }

  suspend fun getStoredNews(): List<Post> {
    val results = ArrayList<Post>()
    val data = newsDao.getNews()
    for (item in data) {
      results.add(item.toPost())
    }
    return results
  }

  override suspend fun getPost(
    id: String,
    liveData: MutableLiveData<Post>,
    error: SingleLiveEvent<String>
  ) {
    GlobalScope.launch(Dispatchers.IO) {
      val entity = newsDao.getPost(id)
      GlobalScope.launch(Dispatchers.Main) {
        liveData.value = entity.toPost()
      }
    }
  }
}

package io.peanutapp.newsfeed.storage

import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class NewsDao: BaseDao<NewsEntity> {
  @Query("SELECT * FROM news")
  abstract fun getNews(): List<NewsEntity>

  @Query("DELETE FROM news")
  abstract suspend fun deleteAll()

  @Query("SELECT * FROM news WHERE uid=:id")
  abstract fun getPost(id: String): NewsEntity
}

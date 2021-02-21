package io.peanutapp.newsfeed.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
  abstract fun newsDao(): NewsDao

  companion object {
    @Volatile
    private var INSTANCE: NewsDatabase? = null

    fun getDatabase(
      context: Context,
    ): NewsDatabase =
      INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          NewsDatabase::class.java,
          "news_database"
        )
          // Wipes and rebuilds instead of migrating if no Migration object.
          // Migration is not part of this codelab.
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
  }
}

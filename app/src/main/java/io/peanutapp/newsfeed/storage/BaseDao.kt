package io.peanutapp.newsfeed.storage

import androidx.room.*

@Dao
interface BaseDao<T> {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: T)

  @Update
  fun update(entity: T)

  @Delete
  fun delete(entity: T)
}

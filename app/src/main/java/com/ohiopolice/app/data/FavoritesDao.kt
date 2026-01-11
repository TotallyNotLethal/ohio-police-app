package com.ohiopolice.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_statutes ORDER BY section")
    fun observeFavorites(): Flow<List<FavoriteStatute>>

    @Query("SELECT * FROM favorite_statutes WHERE section = :section LIMIT 1")
    suspend fun getFavorite(section: String): FavoriteStatute?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavorite(favorite: FavoriteStatute)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteStatute)
}

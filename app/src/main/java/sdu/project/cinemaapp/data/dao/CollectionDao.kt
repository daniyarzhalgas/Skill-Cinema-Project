package sdu.project.cinemaapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import sdu.project.cinemaapp.domain.model.MovieCollection
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Upsert
    suspend fun upsert(movieCollection: MovieCollection)

    @Query("Select * from MovieCollection where collectionName = :collection")
    suspend fun getCollection(collection: String): MovieCollection?

    @Query("Select * from MovieCollection")
    fun getCollections(): Flow<List<MovieCollection>>

    @Query("Delete from MovieCollection where collectionName = :collection")
    suspend fun deleteCollection(collection: String)
}
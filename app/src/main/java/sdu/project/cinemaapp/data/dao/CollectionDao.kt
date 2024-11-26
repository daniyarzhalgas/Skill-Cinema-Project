package sdu.project.cinemaapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import sdu.project.cinemaapp.domain.model.Collection


@Dao
interface CollectionDao {
    @Upsert
    suspend fun setMovieToCollection(movie: Collection)

    @Delete
    suspend fun deleteMovieFromCollection(movie: Collection)

    @Query("Delete from Collection where collectionName = :type")
    suspend fun deleteAllCollectionMovies(type: String)

    @Query("Select * from Collection where collectionName = :type")
    suspend fun getAllCollectionMovies(type: String): List<Collection>
}
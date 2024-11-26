package sdu.project.cinemaapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import sdu.project.cinemaapp.domain.model.WatchedMovie

@Dao
interface WatchedMovieDao {
    @Query("Select * from WatchedMovie")
    suspend fun getAllWatchedMovies():List<WatchedMovie>

    @Upsert
    suspend fun setWatchedMovie(movie: WatchedMovie)

    @Delete
    suspend fun deleteWatchedMovie(movie: WatchedMovie)

    @Query("delete from watchedmovie")
    suspend fun deleteAllWatchedMovies()
}
package sdu.project.cinemaapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import sdu.project.cinemaapp.domain.model.Movie

@Dao
interface MovieDao {
    @Query("Select * from Movie where kinopoiskId = :id")
    suspend fun getMovie(id: Int):Movie

    @Upsert
    suspend fun setMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("Select * from movie where kinopoiskId in (:movieId)")
    suspend fun getMovies(movieId: List<Int>):List<Movie>

}
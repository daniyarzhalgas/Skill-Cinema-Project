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

    @Query("Select * from movie where kinopoiskId in (:movieId)")
    suspend fun getMovies(movieId: List<Int>):List<Movie>

    @Upsert
    suspend fun setMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)



    @Query("select * from movie where isWatched = 1")
    suspend fun getMoviesByWatched(): List<Movie>

    @Query("Update Movie set isWatched = 0 where  isWatched = 1")
    suspend fun deleteAllWatchedMovies()


    @Query("Select * from Movie where collectionName = 'liked'")
    suspend fun getMoviesByLiked() : List<Movie>



}
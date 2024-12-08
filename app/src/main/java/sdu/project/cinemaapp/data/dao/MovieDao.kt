package sdu.project.cinemaapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
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

    @Query("update movie set isWatched = 0 where kinopoiskId = :id")
    suspend fun deleteFromWatched(id: Int)

    @Query("Update Movie set isWatched = 0 where  isWatched = 1")
    suspend fun deleteAllWatchedMovies()


    @Query("SELECT * FROM Movie WHERE collectionName LIKE '%' || :collection || '%'")
    suspend fun getMoviesByCollection(collection: String): List<Movie>

    @Query("select count(collectionName) from movie where collectionName Like '%' || :collection || '%'")
    fun getCollectionCount(collection : String): Flow<Int>

    @Query("SELECT * FROM Movie WHERE :collectionName = collectionName")
    suspend fun getMoviesWithCollection(collectionName: String): List<Movie>

    @Query("UPDATE Movie SET collectionName = :updatedCollection WHERE id = :movieId")
    suspend fun updateMovieCollection(movieId: Int, updatedCollection: List<String>?)

    @Transaction
    suspend fun deleteCollectionAndUpdateMovies(collectionName: String) {
        removeCollectionFromMovies(collectionName)
    }

    suspend fun removeCollectionFromMovies(collectionName: String) {
        val moviesWithCollection = getMoviesWithCollection(collectionName)
        for (movie in moviesWithCollection) {
            val updatedCollection = movie.collectionName?.filterNot { it == collectionName }
            updateMovieCollection(movie.id, updatedCollection)
        }
    }
}
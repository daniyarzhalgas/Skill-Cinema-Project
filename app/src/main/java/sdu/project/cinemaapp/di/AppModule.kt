package sdu.project.cinemaapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sdu.project.cinemaapp.data.dao.CollectionDao
import sdu.project.cinemaapp.data.dao.MovieDao
import sdu.project.cinemaapp.data.dao.WatchedMovieDao
import sdu.project.cinemaapp.data.local.AppDatabase
import sdu.project.cinemaapp.data.remote.MoviesApi
import sdu.project.cinemaapp.domain.model.WatchedMovie
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import sdu.project.cinemaapp.utills.constant.Constant.BASE_URL
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedViewModel() = SharedViewModel()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideCollectionDao(db: AppDatabase): CollectionDao = db.collectionDao()

    @Provides
    fun provideWatchedMovieDao(db: AppDatabase): WatchedMovieDao = db.watchedMovieDao()
}
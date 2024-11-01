package sdu.project.cinemaapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sdu.project.cinemaapp.data.repository.MoviesRepositoryImpl
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductsRepository(impl: MoviesRepositoryImpl): MoviesRepository
}
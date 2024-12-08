package sdu.project.cinemaapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sdu.project.cinemaapp.data.dao.CollectionDao
import sdu.project.cinemaapp.data.dao.MovieDao
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.MovieCollection
import sdu.project.cinemaapp.utills.converter.Converters

@Database(
    entities = [Movie::class, MovieCollection::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun collectionDao(): CollectionDao
}
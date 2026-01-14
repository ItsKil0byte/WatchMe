package ru.ae.watchme.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ae.watchme.data.local.dao.MovieDao
import ru.ae.watchme.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
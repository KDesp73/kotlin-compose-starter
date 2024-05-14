package io.github.kdesp73.myapplication.backend.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 0,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
}

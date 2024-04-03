package com.read.comicbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.read.comicbook.db.query.PostDao
import com.read.comicbook.db.query.UserDao
import com.read.comicbook.db.schema.PostEntity
import com.read.comicbook.db.schema.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ComicBookDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        private const val DATABASE_NAME = "comic_db"

        @Volatile
        private var instance: ComicBookDatabase? = null

        fun getInstance(context: Context): ComicBookDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ComicBookDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ComicBookDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
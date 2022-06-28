package com.example.android_remote.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_remote.dao.RepoDao
import com.example.android_remote.dao.SearchDao
import com.example.android_remote.dao.UserDao
import com.example.android_remote.entity.RepoEntity
import com.example.android_remote.entity.SearchEntity
import com.example.android_remote.entity.UserEntity
import com.example.android_remote.relations.UserWithFollowersCrossRef


@Database(
    entities = [
        RepoEntity::class,
        SearchEntity::class,
        UserEntity::class,
        UserWithFollowersCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class GitHubDataBase : RoomDatabase() {

    abstract fun repoDao() : RepoDao
    abstract fun searchDao() : SearchDao
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var instance: GitHubDataBase? = null
        private val LOCK = Any()

        operator fun invoke(application: Application) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(application).also {
                instance = it
            }
        }

        private fun buildDatabase(application: Application) =
            Room.databaseBuilder(
                application.applicationContext,
                GitHubDataBase::class.java,
                "github_database")
                .build()

    }
}

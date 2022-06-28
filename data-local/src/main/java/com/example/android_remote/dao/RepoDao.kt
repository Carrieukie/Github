package com.example.android_remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_remote.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repository: List<RepoEntity>)

    @Query("select * from repos where owner=:owner")
    fun getReposById(owner : String) : List<RepoEntity>

}
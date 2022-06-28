package com.example.android_remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_remote.entity.RepoEntity
import com.example.android_remote.entity.SearchEntity
import com.example.domain.models.SearchResult

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repoEntity: SearchEntity)

    @Query("select * from searches where login like '%' || :username || '%'")
    fun search(username : String) : SearchEntity?

}
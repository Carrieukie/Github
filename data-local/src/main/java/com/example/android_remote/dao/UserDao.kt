package com.example.android_remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_remote.entity.UserEntity
import com.example.android_remote.relations.UserWithFollowers
import com.example.android_remote.relations.UserWithFollowersCrossRef
import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: List<UserEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFollowers(crossRef: List<UserWithFollowersCrossRef>)

    @Query("select * from users where login=:login")
    fun getFollowersof(login : String): UserWithFollowers

    @Query("select * from users")
    fun getAllUsers() : Flow<List<User>>

    @Query("select * from users where id=:userId")
    fun getUserById(userId : Int) : UserEntity

}

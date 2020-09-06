package com.example.nattramn.core

import androidx.room.*
import com.example.nattramn.features.user.data.UserAndArticle
import com.example.nattramn.features.user.data.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Update
    fun editUser(userEntity: UserEntity)

    @Transaction
    @Query("select * from users")
    fun getUsersAndArticles(): List<UserAndArticle>

}
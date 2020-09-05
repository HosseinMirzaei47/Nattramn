package com.example.nattramn.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.nattramn.features.user.data.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewUser(userEntity: UserEntity)

    /*@Transaction
    @Query("select * from users")
    fun getUsersAndArticles(): List<UserAndArticle>*/

}
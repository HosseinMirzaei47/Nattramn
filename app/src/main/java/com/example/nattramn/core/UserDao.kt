package com.example.nattramn.core

import androidx.room.*
import com.example.nattramn.features.user.data.UserAndArticle
import com.example.nattramn.features.user.data.UserEntity
import java.util.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewUser(userEntity: UserEntity)

    @Transaction
    @Query("select * from users")
    fun getUsersAndArticles(): ArrayList<UserAndArticle>

}
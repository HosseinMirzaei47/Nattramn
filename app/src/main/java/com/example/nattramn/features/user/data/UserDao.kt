package com.example.nattramn.features.user.data

import androidx.room.*

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

    @Query("select * , count(article.title) as count from articles article join users user on user.userId = article .userId ")
    fun getUsersWithArticleCount(): List<UserAndArticleCount>

}
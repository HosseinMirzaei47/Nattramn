package com.example.nattramn.features.user.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Update
    fun editUser(userEntity: UserEntity)

    @Query("select * , count(article.title) as count from articles article join users user on user.userId = article .userId ")
    fun getUsersWithArticleCount(): LiveData<List<UserAndArticleCount>>

    @Transaction
    @Query("select * from users")
    fun getUsersAndArticles(): LiveData<List<UserAndArticle>>

    @Transaction
    @Query("select * from users")
    fun getUserWithArticlesAndCommentsAndTags(): List<UserWithArticleAndCommentsAndTags>

}
package com.example.nattramn.features.user.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("delete from users")
    fun clearUserTable()

    @Query("select * from users")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("select * from users")
    fun getAllUsers2(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Update
    fun editUser(userEntity: UserEntity)

    @Query("select *, count(article.title) as count from users user join articles article on user.userId = article.ownerId group by user.userId")
    fun getUsersWithArticleCount(): List<UserAndArticleCount>

    /*user.userId, user.name, user.image, user.job, user.followers, user.userOwnerId*/

    @Transaction
    @Query("select * from users")
    fun getUsersAndArticles(): LiveData<List<UserAndArticle>>

    @Transaction
    @Query("select * from users")
    fun getUserWithArticlesAndCommentsAndTags(): List<UserWithArticleAndCommentsAndTags>

}
package com.example.nattramn.features.user.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("delete from users")
    fun clearUserTable()

    @Query("select * from users")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Update
    fun editUser(userEntity: UserEntity)

    @Query("select *, count(article.title) as count from users user join articles article on user.userId = article.ownerId group by user.userId")
    fun getUsersWithArticleCount(): LiveData<List<UserAndArticleCount>>

    @Transaction
    @Query("select * from users")
    fun getUsersAndArticles(): LiveData<List<UserAndArticle>>

    @Transaction
    @Query("select * from users")
    fun getUserWithArticlesAndCommentsAndTags(): LiveData<List<UserWithArticleAndCommentsAndTags>>

}
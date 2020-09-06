package com.example.nattramn.features.user.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.article.data.ArticleWithCommentsAndTags
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
@Parcelize
data class UserEntity(
    @PrimaryKey val userId: Int,
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    /*val favoriteArticles: @RawValue ArrayList<ArticleEntity>,
    val publishedArticle: @RawValue ArrayList<ArticleEntity>,*/
    val userOwnerId: Long
) : Parcelable

data class UserAndArticle(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val articleEntity: List<ArticleEntity>
)

data class UserAndArticleCount(
    val count: Int,
    @Embedded val user: UserEntity
)

data class UserWithArticleAndCommentsAndTags(

    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId",
        entity = ArticleEntity::class
    )
    val articleWithCommentsAndTags: List<ArticleWithCommentsAndTags>

)

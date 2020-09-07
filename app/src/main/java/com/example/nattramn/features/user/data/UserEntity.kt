package com.example.nattramn.features.user.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.article.data.ArticleWithCommentsAndTags
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "users",
    primaryKeys = ["user_id"]
)
@Parcelize
data class UserEntity(
    /*@PrimaryKey val userId: Int,*/
    @ColumnInfo(name = "user_id")
    val userId: Int = 1,
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val userOwnerId: Long
) : Parcelable

data class UserAndArticle(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "owner_id"
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
        parentColumn = "user_id",
        entityColumn = "owner_id",
        entity = ArticleEntity::class
    )
    val articleWithCommentsAndTags: List<ArticleWithCommentsAndTags>

)

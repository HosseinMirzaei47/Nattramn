package com.example.nattramn.features.user.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.nattramn.features.article.data.ArticleEntity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "users")
@Parcelize
data class UserEntity(
    @PrimaryKey val userId: Int,
    val name: String,
    val job: String,
    val image: String,
    val followers: Int,
    val favoriteArticles: @RawValue ArrayList<ArticleEntity>,
    val publishedArticle: @RawValue ArrayList<ArticleEntity>,
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
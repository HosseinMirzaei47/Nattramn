package com.example.nattramn.features.user.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nattramn.features.article.data.ArticleEntity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity
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
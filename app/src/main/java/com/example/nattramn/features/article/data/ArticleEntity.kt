package com.example.nattramn.features.article.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Relation
import com.example.nattramn.features.user.data.UserEntity

@Entity(
    tableName = "articles",
    primaryKeys = ["slug"],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            onUpdate = CASCADE,
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["ownerUsername"]
        )
    ]
)
data class ArticleEntity(
    var slug: String,
    var date: String,
    var title: String,
    var body: String,
    var likes: String,
    /*@Ignore val tagList: List<String>,*/
    var favoriteCount: Int,
    var bookmarked: Boolean,
    var ownerUsername: String
)

data class ArticleWithCommentsAndTags(

    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "slug",
        entityColumn = "articleSlug"
    )
    val commentEntity: List<CommentEntity>,

    @Relation(
        parentColumn = "slug",
        entityColumn = "articleSlug"
    )
    val tagEntity: List<TagEntity>

)
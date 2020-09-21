package com.example.nattramn.features.article.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Ignore
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
    var favoriteCount: Int,
    var bookmarked: Boolean,
    var ownerUsername: String
) {

    @Ignore
    lateinit var comments: List<CommentEntity>

    @Ignore
    lateinit var tags: List<TagEntity>

}

/*
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

)*/

package com.read.comicbook.db.query

import androidx.room.*
import com.read.comicbook.db.schema.PostEntity
import com.read.comicbook.db.schema.UserEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE email = :id")
    fun getPost(id: String): PostEntity?

    @Query("SELECT * FROM posts WHERE category = :category")
    fun getPostsByCategory(category: String): List<PostEntity>

    @Query("SELECT * FROM posts WHERE postId IN (:postIds)")
    fun getPostsByIds(postIds: List<Long>): List<PostEntity>

    @Insert
    fun insertPost(post: PostEntity)

    @Update
    fun updatePost(post: PostEntity)

    @Delete
    fun deletePost(post: PostEntity)
}
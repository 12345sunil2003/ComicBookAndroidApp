package com.read.comicbook.home

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.read.comicbook.MyApp
import com.read.comicbook.db.query.PostDao
import com.read.comicbook.db.query.UserDao
import com.read.comicbook.db.schema.PostEntity
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.model.PostInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var allPosts = MutableLiveData<List<PostInfoModel>>()
    var postsByCategory = MutableLiveData<List<PostInfoModel>>()
    fun createPost(model:PostEntity, result: (isSuccess: Boolean, msg: String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPostDao()?.insertPost(model)
                withContext(Dispatchers.Main){
                    result(true,"")
                }
            }catch (e: SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    result(false, "Cannot create post")
                }
            }
        }
    }

    fun getAllPosts(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val entityList = getPostDao()?.getAllPosts()

                allPosts.postValue(mapPostEntityToPostInfoModel(entityList))

            }catch (e: SQLiteConstraintException){
                e.printStackTrace()
            }
        }
    }

    fun getPostsByCategory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val entityList = getPostDao()?.getPostsByCategory(name)

                postsByCategory.postValue(mapPostEntityToPostInfoModel(entityList))

            }catch (e: SQLiteConstraintException){
                e.printStackTrace()
            }
        }
    }

    fun getPostsById(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val entityList = getPostDao()?.getPostsByIds(ids.map { it.toLong() })

                postsByCategory.postValue(mapPostEntityToPostInfoModel(entityList))

            }catch (e: SQLiteConstraintException){
                e.printStackTrace()
            }
        }
    }

    private fun mapPostEntityToPostInfoModel(entityList: List<PostEntity>?): List<PostInfoModel>? {
        return entityList?.map {
            PostInfoModel(
                postId = it.postId,
                title = it.title,
                desc = it.desc,
                thumbnail = it.thumbnail,
                category = it.category,
                story = null,
                imgFiles = it.imgList,
                isFav = false,
                rating = it.rating,
                price = it.price,
                isPaid = it.isPremium?:false
            )
        }
    }

    private fun getPostDao(): PostDao? {
        return getApplication<MyApp>().getDb()?.postDao()
    }


}
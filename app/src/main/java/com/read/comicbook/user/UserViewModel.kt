package com.read.comicbook.user

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.read.comicbook.MyApp
import com.read.comicbook.SharedPreferencesManager
import com.read.comicbook.db.query.UserDao
import com.read.comicbook.db.schema.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<UserEntity?>()
    fun insertUser(model: UserEntity, result: (isSuccess: Boolean, msg: String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getUserDao()?.insertUser(model)
                withContext(Dispatchers.Main){
                    result(true,"")
                }
            }catch (e: SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    if (e.localizedMessage?.contains("UNIQUE constraint failed") == true) {
                        result(false, "User already exists")
                    } else {
                        result(false, "Cannot create user")
                    }
                }
            }
        }
    }

    fun login(email: String, pass: String, result: (isSuccess: Boolean, msg: String) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getUserDao()?.getUsers(email, pass)
                withContext(Dispatchers.Main) {
                    if (data == null) {
                        result(false, "User not found")
                    }else{
                        result(true, "")
                        getApplication<MyApp>().apply {
                            setUser(data)
                            saveUserPref(data.userId)
                        }
                    }
                }
            }catch (e: SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    result(false, "User not found")
                }
            }
        }
    }

    fun updateUser(userEntity: UserEntity, result: (isSuccess: Boolean, msg: String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getUserDao()?.updateUser(userEntity)
                getApplication<MyApp>().setUser(userEntity)
                withContext(Dispatchers.Main){
                    result(true,"")
                }
            }catch (e: SQLiteConstraintException){
                withContext(Dispatchers.Main) {
                    result(false, "Cannot update user")
                }
            }
        }
    }

    private fun getUserDao(): UserDao? {
        return getApplication<MyApp>().getDb()?.userDao()
    }

    fun getUserById(userId:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getUserDao()?.getUserById(userId)
                user.postValue(data)
            }catch (e: SQLiteConstraintException){
                e.printStackTrace()
            }
        }
    }


}
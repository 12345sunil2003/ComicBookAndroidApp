package com.read.comicbook.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromHashMap(value: HashMap<String, Boolean>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toHashMap(value: String?): HashMap<String, Boolean>? {
        val type = object : TypeToken<HashMap<String, Boolean>>() {}.type
        return Gson().fromJson(value, type)
    }

}

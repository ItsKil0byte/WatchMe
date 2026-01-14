package ru.ae.watchme.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        return if (value.isNullOrBlank()){
            null
        }
        else{
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(value , listType)
        }
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String? {
        return list.let { gson.toJson(it) }
    }
}
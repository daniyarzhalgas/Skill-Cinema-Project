package sdu.project.cinemaapp.utills.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonNull
import com.google.gson.reflect.TypeToken
import sdu.project.cinemaapp.domain.model.Country
import sdu.project.cinemaapp.domain.model.Genre

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromCountryList(countries: List<Country>?): String? {
        return gson.toJson(countries)
    }

    @TypeConverter
    fun toCountryList(data: String?): List<Country>? {
        val listType = object : TypeToken<List<Country>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String? {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenreList(data: String?): List<Genre>? {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromGenre(genre: Genre?):String? {
        return gson.toJson(genre)
    }

    @TypeConverter
    fun toGenre(data: String?): Genre?{
        val type = object : TypeToken<Genre>() {}.type
        return gson.fromJson(data, type)
    }
}
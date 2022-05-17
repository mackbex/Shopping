package com.item.shopping.data.source.local
import androidx.room.TypeConverter
import java.util.*

/**
 * Room TypeConverter
 */
class TypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
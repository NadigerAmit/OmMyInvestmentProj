package com.amitnadiger.myinvestment.utility

import androidx.room.TypeConverter
import java.util.*

class DateConverters {
    @TypeConverter
    fun fromTimeMillis(millis: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal
    }

    @TypeConverter
    fun toTimeMillis(cal: Calendar): Long {
        return cal.timeInMillis
    }
}
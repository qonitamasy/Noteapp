package com.qonita.noteapp.data.model

import android.renderscript.RenderScript
import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun  fromPriority(priority: priority) : String{
        return  priority.name
    }

    @TypeConverter
    fun toPriority(priority: String) : priority{
        return priority.valueOf(priority)
    }
}
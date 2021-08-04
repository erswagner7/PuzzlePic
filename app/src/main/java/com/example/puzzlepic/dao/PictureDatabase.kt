package com.example.puzzlepic.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.puzzlepic.dto.Picture

@Database(entities=arrayOf(Picture::class),version = 1)
@TypeConverters(Converters::class)
abstract class PictureDatabase : RoomDatabase() {
    abstract fun localPictureDAO() : ILocalPictureDAO
}
package com.example.puzzlepic.dao

import androidx.room.TypeConverter
import com.example.puzzlepic.dto.UnsplashPhotoUrls

class Converters {
        @TypeConverter
        fun fromUnsplashUrls(value: UnsplashPhotoUrls): String {
            return value.raw.toString()
        }

        @TypeConverter
        fun toUnsplashUrl(value: String): UnsplashPhotoUrls {
            return UnsplashPhotoUrls(raw = value.toString())
        }
    }



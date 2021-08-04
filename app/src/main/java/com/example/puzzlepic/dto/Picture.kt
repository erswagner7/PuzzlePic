package com.example.puzzlepic.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName="picture")
data class Picture(@PrimaryKey @SerializedName("id") var pictureID : String = "",
                   @SerializedName("alt_description") var pictureTitle : String = "",
                   @SerializedName("created_at") var date : String = "",
                   var location : String = "",
                   var notes : String = "",
                   var puzzleID : Int = 0,
                   @SerializedName("urls") var urls : UnsplashPhotoUrls = UnsplashPhotoUrls(),
                   var localUri : String = "" ) {
        override fun toString() : String {
            return "$pictureTitle: $date"
        }

    }

@Parcelize
data class UnsplashPhotoUrls(
    val raw: String = "",
    val full: String = "",
    val regular: String = "",
    val small: String = "",
    val thumb: String= "",
) : Parcelable


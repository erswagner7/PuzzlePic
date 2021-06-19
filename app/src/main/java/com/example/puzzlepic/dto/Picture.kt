package com.example.puzzlepic.dto

import com.google.gson.annotations.SerializedName

class Picture {
    data class Picture(@SerializedName("id") var pictureID : Int = 0, var pictureTitle : String, var date : String, var location : String, var notes : String,
                       @SerializedName("id") var puzzleID : Int = 0){
        override fun toString() : String {
            return pictureTitle
            return date
        }
    }
}
package com.example.puzzlepic.dao

import android.graphics.Picture
import retrofit2.http.GET
import retrofit2.Call
interface IPictureDAO {

    @GET("")
    fun getAllPictures(): Call<ArrayList<Picture>> //going to return a collection of pictures.

    @GET("")
    fun getPictures(plantName:String) : Call<ArrayList<Picture>> //going to return a specific picture.
}
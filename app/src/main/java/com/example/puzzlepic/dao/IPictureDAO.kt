package com.example.puzzlepic.dao

import com.example.puzzlepic.dto.Picture
import retrofit2.Call
import retrofit2.http.GET


interface IPictureDAO {

    @GET("/photos?client_id=gNYmyVdZ2_qHp3L4i4yzrulyHU70MdvnmsFFyB_5y38&per_page=100")
    fun getRandomImage(): Call<ArrayList<Picture>>

    //
    //@GET("")
    //fun getPictureByName(pictureName:String) : Call<ArrayList<Picture>> //going to return a specific picture.
}
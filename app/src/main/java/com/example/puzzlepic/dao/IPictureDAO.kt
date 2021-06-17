package com.example.puzzlepic.dao

import com.example.puzzlepic.dto.Picture
import retrofit2.Call
import retrofit2.http.GET



interface IPictureDAO {

    @GET("/photos/random?client_id=gNYmyVdZ2_qHp3L4i4yzrulyHU70MdvnmsFFyB_5y38")//TODO move access key to header interceptor
    fun getRandomImage(): Call<ArrayList<Picture>>
}
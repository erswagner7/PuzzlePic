package com.example.puzzlepic

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File



object RetrofitClientInstance {
    private var retrofit: Retrofit? = null;
    private val BASE_URL = "https://api.unsplash.com"
    private val Access_Key = "client_id=gNYmyVdZ2_qHp3L4i4yzrulyHU70MdvnmsFFyB_5y38"

    val retrofitInstance : Retrofit?

    get(){
        createRetrofitObject()
        return retrofit
    }

    fun createRetrofitObject() =
        when(retrofit) {
            null -> RetrofitClientInstance.retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            else -> {
                //retrofit object already exists
                File("application.log").writeText("Tried to create new retrofit object, object already exists")
            }
        }
}
//
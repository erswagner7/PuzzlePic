package com.example.puzzlepic

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitClientInstance {
    private var retrofit: Retrofit? = null;
    private val BASE_URL = "https://api.unsplash.com"
    private val Access_Key = "client_id=gNYmyVdZ2_qHp3L4i4yzrulyHU70MdvnmsFFyB_5y38"

    val retrofitInstance : Retrofit?

    get(){
        //has this object been created?
        if(retrofit == null){
            //then create it
            retrofit = retrofit2.Retrofit.Builder()//TODO add header interceptor for unsplash access key
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}
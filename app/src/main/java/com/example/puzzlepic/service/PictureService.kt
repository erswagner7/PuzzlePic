package com.example.puzzlepic.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.puzzlepic.RetrofitClientInstance
import com.example.puzzlepic.dao.IPictureDAO
import com.example.puzzlepic.dto.Picture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A service that accesses a json endpoint with freely-usable images
 *
 * This class will be used to provide our application with a random picture to convert into a sliding puzzle
 *
 */

class PictureService {
    
    /**
     * Uses a callback to retrieve json data from network and handles response accordingly
     * @return an LiveArrayList of pictures.
     */
    fun fetchPicture() : MutableLiveData<ArrayList<Picture>> {

        var picture = MutableLiveData<ArrayList<Picture>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(IPictureDAO::class.java)
        val call = service?.getRandomImage()
        call?.enqueue(object: Callback<ArrayList<Picture>>{

            /**
             * Callback method to be invoked when the response contains the expected picture data
             */
            override fun onResponse(
                call: Call<ArrayList<Picture>>,
                response: Response<ArrayList<Picture>>
            ) {
                picture.value = response.body()
            }

            /**
             * Callback method to be invoked when the response fails  //TODO implement method
             */
            override fun onFailure(call: Call<ArrayList<Picture>>, t: Throwable) {
                Log.e("ImageRetreivalFailure","Unable to retrieve image from api")
            }


        })
        return picture
    }
}
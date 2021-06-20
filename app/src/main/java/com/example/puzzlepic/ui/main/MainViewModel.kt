package com.example.puzzlepic.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.puzzlepic.dto.Picture
import com.example.puzzlepic.service.PictureService

class MainViewModel : ViewModel() {

    var picture: MutableLiveData<ArrayList<Picture>> = MutableLiveData<ArrayList<Picture>>()
    var pictureService: PictureService = PictureService()


    init{
        fetchPictures()
    }

    /*
    Loads an array of unsplash Pictures into the view model using retrofit
    */
    fun fetchPictures(){
        picture = pictureService.fetchPicture()
    }
}
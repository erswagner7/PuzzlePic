package com.example.puzzlepic.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.puzzlepic.dto.Picture
import com.example.puzzlepic.service.PictureService

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var picture: MutableLiveData<ArrayList<Picture>> = MutableLiveData<ArrayList<Picture>>()
    var pictureService: PictureService = PictureService()

    fun fetchPictures(pictureName: String){
        //line 16 needs help --> uncomment it and an error shows up.
        //I commented it out so it wouldn't break other things.
     //   pictures = pictureService.fetchPicture(pictureName)
    }
}
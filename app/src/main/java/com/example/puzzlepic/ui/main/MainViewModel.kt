package com.example.puzzlepic.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.puzzlepic.dto.Picture
import com.example.puzzlepic.service.PictureService


class MainViewModel : ViewModel() {

    private var _picture: MutableLiveData<ArrayList<Picture>> = MutableLiveData<ArrayList<Picture>>()
    private var _pictureService: PictureService = PictureService()

    init{
        fetchPictures()
    }



    /*
    Loads an array of unsplash Pictures into the view model using retrofit
    */
    fun fetchPictures(){
        _picture = _pictureService.fetchPicture()
    }

    internal var picture: MutableLiveData<ArrayList<Picture>>
        get() {return _picture}
        set(value) {_picture = value}

    internal var pictureService : PictureService
    get() {return _pictureService}
    set(value){_pictureService = value}

}
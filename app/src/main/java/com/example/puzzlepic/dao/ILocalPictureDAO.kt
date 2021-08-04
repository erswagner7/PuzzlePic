package com.example.puzzlepic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.puzzlepic.dto.Picture

@Dao
interface ILocalPictureDAO {
    @Query("SELECT * FROM picture")
    fun getAllPictures()  : LiveData<List<Picture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pictures: ArrayList<Picture>)

    @Delete
    fun delete(picture: Picture)

}
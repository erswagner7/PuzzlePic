@file:Suppress("DEPRECATION")

package com.example.puzzlepic.ui.main

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.puzzlepic.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private val IMAGE_GALLERY_REQUEST_CODE: Int = 2001
    private val SAVE_IMAGE_REQUEST_CODE: Int = 1999
    private val CAMERA_REQUEST_CODE: Int = 1998

    val CAMERA_PERMISSION_REQUEST_CODE = 1997

    private lateinit var currentPhotoPath: String

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        button3.setOnClickListener {
            testRandomPhoto()
        }
    }


    /**
     * This will set the center photo box with a photo from the internet when the "Get Random Puzzle Button" is selected
     */
    private fun testRandomPhoto() {
         val Image_Url = "https://images.unsplash.com/photo-1622182126375-fb50f349dea2?ixid=MnwyNDAxMTN8MHwxfHJhbmRvbXx8fHx8fHx8fDE2MjQwMzA0NjE\\u0026ixlib=rb-1.2.1"
        val imageView = image5
        Picasso
            .with(context)
            .load(Image_Url)
            .into(imageView)
    }


    //TODO for later use
    private fun createImageFile() : File {
        // genererate a unique filename with date.
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // get access to the directory where we can write pictures.
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("puzzlePic${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }
}
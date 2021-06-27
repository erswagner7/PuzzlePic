@file:Suppress("DEPRECATION")

package com.example.puzzlepic.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        navBarCameraButton.setOnClickListener {
            prepTakePhoto()
        }

        buttonRandomPuzzle.setOnClickListener {
            val randomValue =
                (0..viewModel.picture.value!!.size).random() // generated random from 0 to size of current photo array included
            var randomURL = viewModel.picture.value?.get(randomValue)!!.urls.raw.toString()
            testRandomPhoto(randomURL)
        }
    }

    /**
     * See if there is permission to take photo
     */
    private fun prepTakePhoto() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    Toast.makeText(
                        context,
                        "Unable to take photo without permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                image1.setImageBitmap(imageBitmap)
            }
        }

    }


    /**
     * This will set the center photo box with a photo from the internet when the "Get Random Puzzle Button" is selected
     */
    private fun testRandomPhoto(randomURL: String = "") {
        val imageView = image5
        Picasso
            .with(context)
            .load(randomURL)
            .resize(300, 300)
            .into(imageView)
    }

    //TODO for later use
    private fun createImageFile(): File {
        // genererate a unique filename with date.
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // get access to the directory where we can write pictures.
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("puzzlePic${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }
}
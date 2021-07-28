package com.example.puzzlepic.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

open class SuperFragment: Fragment() {
    protected val SAVE_IMAGE_REQUEST_CODE: Int = 1999
    protected val CAMERA_PERMISSION_REQUEST_CODE = 1997
    private lateinit var currentPhotoPath: String
    protected var puzzleURI : Uri? = null

    /**
     * See if there is permission to take photo
     */
    protected fun prepTakePhoto(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    /**
     * Will take a photo a save it to be later shuffled/randomized into a puzzle
     */
    protected fun takePhoto(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)
            if (takePictureIntent == null) {
                Toast.makeText(context, "Unable to save puzzle", Toast.LENGTH_LONG).show()
            } else {
                val photoFile: File = createImageFile()
                photoFile?.also {
                        puzzleURI = FileProvider.getUriForFile(
                        requireActivity().applicationContext,
                        "com.puzzlepic.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
                    startActivityForResult(takePictureIntent, SAVE_IMAGE_REQUEST_CODE)
                }
            }
        }
    }

    protected fun createImageFile() : File {
        // genererate a unique filename with date.
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // get access to the directory where we can write pictures.
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PuzzlePic${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        when(requestCode){
            // app-level request code responses here
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                } else {
                    Toast.makeText(context, "Unable to take photo without permission", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                // call to superclass
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}
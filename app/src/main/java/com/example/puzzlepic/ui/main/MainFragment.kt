@file:Suppress("DEPRECATION")

package com.example.puzzlepic.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.puzzlepic.R
import com.example.puzzlepic.dto.Picture
import com.example.puzzlepic.databinding.MainFragmentBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private val PUZZLE_GALLERY_REQUEST_CODE: Int = 2001
    private val SAVE_IMAGE_REQUEST_CODE: Int = 1999
    private val CAMERA_REQUEST_CODE: Int = 1998
    val CAMERA_PERMISSION_REQUEST_CODE = 1997

    private lateinit var currentPhotoPath: String
    lateinit var navController: NavController

    protected var puzzleURI : Uri? = null
    private var puzzles : ArrayList<Picture> = ArrayList<Picture>()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        binding.navBarCameraButton.setOnClickListener{
            prepTakePhoto()
        }

        binding.randomPuzzleButton.setOnClickListener {
            val rnds = (0..viewModel.picture.value!!.size).random() // generated random from 0 to size of current photo array included
            var randomURL = viewModel.picture.value?.get(rnds)!!.urls.raw.toString()
            if(randomURL != null) {
                val bundle = bundleOf("url" to randomURL)

                /**
                 * This will take us to our puzzle fragment using the action defined in our nav_graph
                 * with a url as an argument to pass in the
                 * bundle
                 */
                navController!!.navigate(R.id.action_mainFragment_to_puzzleFragment, bundle)
            }
        }

        binding.navBarImageButton.setOnClickListener {
            prepOpenUserPuzzleGallery()
        }

    }

    /*
     * will request permission for and open users puzzle
     * gallery by filling in image windows with saved puzzles
     */
    private fun prepOpenUserPuzzleGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            startActivityForResult(this, PUZZLE_GALLERY_REQUEST_CODE)
        }
    }

    /**
     * See if there is permission to take photo
     */
    private fun prepTakePhoto(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }


        override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                } else {
                    Toast.makeText(context, "Unable to take photo without permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    /**
     * Will take a photo a save it to be later shuffled/randomized into a puzzle
     */
    private fun takePhoto(){
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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                binding.image1.setImageBitmap(imageBitmap)
            } else if (requestCode == SAVE_IMAGE_REQUEST_CODE) {
                Toast.makeText(context, "Puzzle Saved", Toast.LENGTH_LONG).show()
                var puzzle = Picture(localUri = puzzleURI.toString())
                puzzles.add(puzzle)
            } else if (requestCode == PUZZLE_GALLERY_REQUEST_CODE) {
                if (data != null && data.data != null) {
                    val image = data.data
                    val source =
                        ImageDecoder.createSource(requireActivity().contentResolver, image!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.image1.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun createImageFile() : File {
        // genererate a unique filename with date.
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // get access to the directory where we can write pictures.
        val storageDir:File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PuzzlePic${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
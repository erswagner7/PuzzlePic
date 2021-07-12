@file:Suppress("DEPRECATION")
package com.example.puzzlepic.ui.puzzle

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.puzzlepic.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.puzzle_fragment.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class PuzzleFragment : Fragment() {

    lateinit var randomUrl: String
    lateinit var navController: NavController
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        /**
        * here we retrieve a url to load an image from which was sent over the navigation bundle from
         * main_fragment
         */
        randomUrl = requireArguments().getString("url").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.puzzle_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (randomUrl !== null) {
            testRandomPhoto(randomUrl)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         *currently, on this screen we can navigate back to the main component using the "imageButton"
         * on the navbar which will navigate us back using the action defined in our nav_graph
         */
        navBarImageButton.setOnClickListener {
            navController!!.navigate(R.id.action_puzzleFragment_to_mainFragment)
        }


        save_puzzle_button.setOnClickListener{
            val drawable = bigPicture.drawable
            val bitmap = (drawable as BitmapDrawable).bitmap
            val uri:Uri = storeImage(bitmap)
            bigPicture.isVisible = false
            Save_Success_Text.isVisible = true
            Save_Success_Text.text = "saved: $uri"
        }
    }

    private fun storeImage(image : Bitmap): Uri {

        // Create a file to save the image
        val file = createImageFile()

            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress the bitmap
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the output stream
            stream.flush()

            // Close the output stream
            stream.close()
        // Return the saved image path to uri
        return Uri.parse(file.absolutePath)
    }

    /**
     * This will set the center photo box with a photo from internet using a url sent through the navigation bundle
     * from the main_fragment when the
     * "Get Random Puzzle Button" is selected
     */
    private fun testRandomPhoto(randomURL : String = "") {
        //TODO add loading modal while picture is retrieved
        val Image_Url = randomURL
        val imageView = bigPicture
        Picasso
            .with(context)
            .load(Image_Url)
            .resize(320,450)
            .into(imageView)
    }

    private fun createImageFile() : File {
        // genererate a unique filename with date.
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // get access to the directory where we can write pictures.
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PuzzlePic${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

}
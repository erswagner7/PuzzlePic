@file:Suppress("DEPRECATION")
package com.example.puzzlepic.ui.puzzle

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.puzzlepic.R
import com.example.puzzlepic.databinding.PuzzleFragmentBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PuzzleFragment : Fragment() {

    private var _binding: PuzzleFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentPhotoPath: String
    lateinit var randomUrl: String
    lateinit var navController: NavController


    private val gameArray = arrayListOf<Int>(1,2,3,4,5,6,7,8,0)
    private lateinit var finalPiece: Bitmap


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
        _binding = PuzzleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (randomUrl !== null) {
            testRandomPhoto(randomUrl)
        }
        initiateLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            loadingComplete()
        }, 5000)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         *currently, on this screen we can navigate back to the main component using the "imageButton"
         * on the navbar which will navigate us back using the action defined in our nav_graph
         */
        binding.navBarImageButton.setOnClickListener {
            navController!!.navigate(R.id.action_puzzleFragment_to_mainFragment)
        }


        binding.savePuzzleButton.setOnClickListener{
            val drawable = binding.bigPicture.drawable
            val bitmap = (drawable as BitmapDrawable).bitmap
            val uri:Uri = storeImage(bitmap)
            binding.bigPicture.isVisible = false
            hidePuzzleSquares()
            binding.savePuzzleButton.isVisible = false
            binding.puzzlifyButton.isVisible = false
            binding.cancelButton.text = "Return to Home"
            binding.saveSuccessText.isVisible = true
            binding.saveSuccessText.text = "saved: $uri"
        }

        binding.cancelButton.setOnClickListener{
            navController!!.navigate(R.id.action_puzzleFragment_to_mainFragment)
        }

        binding.puzzlifyButton.setOnClickListener{
            binding.savePuzzleButton.text = "Save Puzzle"
            val drawable = binding.bigPicture.drawable
            val fullBitmap = (drawable as BitmapDrawable).bitmap
            binding.bigPicture.isVisible = false

            var bitMapArray = ArrayList<Bitmap>()

            bitMapArray.add(Bitmap.createBitmap(fullBitmap,0,0,fullBitmap.width/3,fullBitmap.height/3))
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,fullBitmap.width/3,0,fullBitmap.width/3,fullBitmap.height/3))
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,(fullBitmap.width/3) * 2,0,fullBitmap.width/3,fullBitmap.height/3))
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,0 ,fullBitmap.height/3,fullBitmap.width/3,fullBitmap.height/3))
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,fullBitmap.width/3 ,fullBitmap.height/3,fullBitmap.width/3,fullBitmap.height/3))
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,(fullBitmap.width/3) * 2  ,fullBitmap.height/3,fullBitmap.width/3,fullBitmap.height/3))
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,0  ,(fullBitmap.height/3) * 2 ,fullBitmap.width/3,fullBitmap.height/3) )
            bitMapArray.add(Bitmap.createBitmap(fullBitmap,fullBitmap.width/3  ,(fullBitmap.height/3) * 2 ,fullBitmap.width/3,fullBitmap.height/3))
            finalPiece = Bitmap.createBitmap(fullBitmap,(fullBitmap.width/3) * 2  ,(fullBitmap.height/3) * 2 ,fullBitmap.width/3,fullBitmap.height/3)

            var tempArray = arrayListOf(0,1,2,3,4,5,6,7)


            var imageView = binding.puzzleSquare1
            var tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[0] = tempIndex + 1

            imageView = binding.puzzleSquare2
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[1] = tempIndex + 1

            imageView = binding.puzzleSquare3
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[2] = tempIndex + 1

            imageView = binding.puzzleSquare4
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[3] = tempIndex + 1

            imageView = binding.puzzleSquare5
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[4] = tempIndex + 1

            imageView = binding.puzzleSquare6
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[5] = tempIndex + 1

            imageView = binding.puzzleSquare7
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[6] = tempIndex + 1

            imageView = binding.puzzleSquare8
            tempIndex = tempArray.random()
            tempArray.remove(tempIndex)
            imageView.setImageBitmap(bitMapArray[tempIndex])
            gameArray[7] = tempIndex + 1

            imageView = binding.puzzleSquare9
            gameArray[8] = 0
            //imageView.setImageBitmap(finalPiece)

            showPuzzleSquares()
            binding.puzzleSquare9.isVisible = false

            binding.puzzlifyButton.text = "Randomize"
            binding.saveSuccessText.isVisible = false
        }

        binding.puzzleSquare1.setOnClickListener{
            var numberToFill = fillSquare(1)
            if(numberToFill != 0){
            updateArray(1,numberToFill,  binding.puzzleSquare1)
            }
        }

        binding.puzzleSquare2.setOnClickListener{
            var numberToFill = fillSquare(2)
            if(numberToFill != 0){
                updateArray(2,numberToFill,  binding.puzzleSquare2)
            }
        }

        binding.puzzleSquare3.setOnClickListener{
            var numberToFill = fillSquare(3)
            if(numberToFill != 0){
                updateArray(3,numberToFill,  binding.puzzleSquare3)
            }
        }

        binding.puzzleSquare4.setOnClickListener{
            var numberToFill = fillSquare(4)
            if(numberToFill != 0){
                updateArray(4,numberToFill,  binding.puzzleSquare4)
            }
        }

        binding.puzzleSquare5.setOnClickListener{
            var numberToFill = fillSquare(5)
            if(numberToFill != 0){
                updateArray(5,numberToFill,  binding.puzzleSquare5)
            }
        }

        binding.puzzleSquare6.setOnClickListener{
            var numberToFill = fillSquare(6)
            if(numberToFill != 0){
                updateArray(6,numberToFill,  binding.puzzleSquare6)
            }
        }

        binding.puzzleSquare7.setOnClickListener{
            var numberToFill = fillSquare(7)
            if(numberToFill != 0){
                updateArray(7,numberToFill, binding.puzzleSquare7)
            }
        }

        binding.puzzleSquare8.setOnClickListener{
            var numberToFill = fillSquare(8)
            if(numberToFill != 0){
                updateArray(8,numberToFill, binding.puzzleSquare8)
            }
        }

        binding.puzzleSquare9.setOnClickListener{
            var numberToFill = fillSquare(9)
            if(numberToFill != 0){
                updateArray(9,numberToFill, binding.puzzleSquare9)
            }
        }

    }

    private fun updateArray(numberFilling: Int, numberToFill: Int, imageView : ImageView) {
        var fillValue = gameArray[numberFilling - 1]
        gameArray[numberFilling - 1 ] = 0
        gameArray[numberToFill - 1] = fillValue
        if(!checkWin()){
            imageView.isVisible = false
        }
    }

    private fun checkWin(): Boolean {
        if (gameArray.equals(arrayOf(1,2,3,4,5,6,7,8,0))){
            var imageView = binding.puzzleSquare9
            imageView.setImageBitmap(finalPiece)
            binding.puzzleSquare9.isVisible = true
            binding.saveSuccessText.isVisible = true
            initiateLoading()
            Handler(Looper.getMainLooper()).postDelayed({
                loadingComplete()
            }, 3500)
            Handler(Looper.getMainLooper()).postDelayed({
               hidePuzzleSquares()
            }, 4000)
            binding.saveSuccessText.text = "Puzzle Complete. Nice Job!"
            binding.puzzlifyButton.text = "Reset"
            return true
              }
        return false
         }

    private fun fillSquare(x: Int): Int {
        return when (x) {
            1 -> {
                var drawableSquare = binding.puzzleSquare1.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare2.isVisible) {
                    binding.puzzleSquare2.setImageBitmap(imageToPass)
                    binding.puzzleSquare2.isVisible = true
                    return 2
                } else if (!binding.puzzleSquare4.isVisible) {
                    binding.puzzleSquare4.setImageBitmap(imageToPass)
                    binding.puzzleSquare4.isVisible = true
                    return 4
                } else {
                    return 0
                }
            }
            2 -> {
                var drawableSquare = binding.puzzleSquare2.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare3.isVisible) {
                    binding.puzzleSquare3.setImageBitmap(imageToPass)
                    binding.puzzleSquare3.isVisible = true
                    return 3
                } else if (!binding.puzzleSquare1.isVisible) {
                    binding.puzzleSquare1.setImageBitmap(imageToPass)
                    binding.puzzleSquare1.isVisible = true
                    return 1
                } else if (!binding.puzzleSquare5.isVisible) {
                    binding.puzzleSquare5.setImageBitmap(imageToPass)
                    binding.puzzleSquare5.isVisible = true
                    return 5
                } else {
                    return 0
                }
            }
            3 -> {
                var drawableSquare = binding.puzzleSquare3.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare2.isVisible) {
                    binding.puzzleSquare2.setImageBitmap(imageToPass)
                    binding.puzzleSquare2.isVisible = true
                    return 2
                } else if (!binding.puzzleSquare6.isVisible) {
                    binding.puzzleSquare6.setImageBitmap(imageToPass)
                    binding.puzzleSquare6.isVisible = true
                    return 6
                } else {
                    return 0
                }
            }
            4 -> {
                var drawableSquare = binding.puzzleSquare4.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare1.isVisible) {
                    binding.puzzleSquare1.setImageBitmap(imageToPass)
                    binding.puzzleSquare1.isVisible = true
                    return 1
                } else if (!binding.puzzleSquare5.isVisible) {
                    binding.puzzleSquare5.setImageBitmap(imageToPass)
                    binding.puzzleSquare5.isVisible = true
                    return 5
                }
                    else if (!binding.puzzleSquare7.isVisible) {
                        binding.puzzleSquare7.setImageBitmap(imageToPass)
                        binding.puzzleSquare7.isVisible = true
                        return 7
                } else {
                    return 0
                }
            }
            5 -> {
                var drawableSquare = binding.puzzleSquare5.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare2.isVisible) {
                    binding.puzzleSquare2.setImageBitmap(imageToPass)
                    binding.puzzleSquare2.isVisible = true
                    return 2
                } else if (!binding.puzzleSquare4.isVisible) {
                    binding.puzzleSquare4.setImageBitmap(imageToPass)
                    binding.puzzleSquare4.isVisible = true
                    return 4
                } else if (!binding.puzzleSquare6.isVisible) {
                    binding.puzzleSquare6.setImageBitmap(imageToPass)
                    binding.puzzleSquare6.isVisible = true
                    return 6
                } else if (!binding.puzzleSquare8.isVisible) {
                    binding.puzzleSquare8.setImageBitmap(imageToPass)
                    binding.puzzleSquare8.isVisible = true
                    return 8
                } else {
                    return 0
                }
            }
            6 -> {
                var drawableSquare = binding.puzzleSquare6.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare3.isVisible) {
                    binding.puzzleSquare3.setImageBitmap(imageToPass)
                    binding.puzzleSquare3.isVisible = true
                    return 3
                } else if (!binding.puzzleSquare5.isVisible) {
                    binding.puzzleSquare5.setImageBitmap(imageToPass)
                    binding.puzzleSquare5.isVisible = true
                    return 5
                } else if (!binding.puzzleSquare9.isVisible) {
                    binding.puzzleSquare9.setImageBitmap(imageToPass)
                    binding.puzzleSquare9.isVisible = true
                    return 9
                } else {
                    return 0
                }
            }
            7 -> {
                var drawableSquare = binding.puzzleSquare7.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare4.isVisible) {
                    binding.puzzleSquare4.setImageBitmap(imageToPass)
                    binding.puzzleSquare4.isVisible = true
                    return 4
                } else if (!binding.puzzleSquare8.isVisible) {
                    binding.puzzleSquare8.setImageBitmap(imageToPass)
                    binding.puzzleSquare8.isVisible = true
                    return 8
                } else {
                    return 0
                }
            }
            8 -> {
                var drawableSquare = binding.puzzleSquare8.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare7.isVisible) {
                    binding.puzzleSquare7.setImageBitmap(imageToPass)
                    binding.puzzleSquare7.isVisible = true
                    return 7
                } else if (!binding.puzzleSquare5.isVisible) {
                    binding.puzzleSquare5.setImageBitmap(imageToPass)
                    binding.puzzleSquare5.isVisible = true
                    return 5
                } else if (!binding.puzzleSquare9.isVisible) {
                    binding.puzzleSquare9.setImageBitmap(imageToPass)
                    binding.puzzleSquare9.isVisible = true
                    return 9
                } else {
                    return 0
                }
            }
            9 -> {
                var drawableSquare = binding.puzzleSquare9.drawable
                var imageToPass = (drawableSquare as BitmapDrawable).bitmap
                if (!binding.puzzleSquare6.isVisible) {
                    binding.puzzleSquare6.setImageBitmap(imageToPass)
                    binding.puzzleSquare6.isVisible = true
                    return 6
                } else if (!binding.puzzleSquare8.isVisible) {
                    binding.puzzleSquare8.setImageBitmap(imageToPass)
                    binding.puzzleSquare8.isVisible = true
                    return 8
                } else {
                    return 0
                }
            }
            else -> return 0
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
        val Image_Url = randomURL
        val imageView = binding.bigPicture
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

    private fun initiateLoading() {
        binding.progressBar.isVisible = true
        toggleButtons("off")
    }

    private fun loadingComplete(){
        if(binding.bigPicture.drawable != null){
            binding.progressBar.isVisible = false
            toggleButtons("on")
        }
        else{
            Handler(Looper.getMainLooper()).postDelayed({
                loadingComplete()
            }, 3000)
        }
    }

    private fun showPuzzleSquares() {
        binding.puzzleSquare1.isVisible = true
        binding.puzzleSquare2.isVisible = true
        binding.puzzleSquare3.isVisible = true
        binding.puzzleSquare4.isVisible = true
        binding.puzzleSquare5.isVisible = true
        binding.puzzleSquare6.isVisible = true
        binding.puzzleSquare7.isVisible = true
        binding.puzzleSquare8.isVisible = true
    }

    private fun hidePuzzleSquares() {
        binding.puzzleSquare1.isVisible = false
        binding.puzzleSquare2.isVisible = false
        binding.puzzleSquare3.isVisible = false
        binding.puzzleSquare4.isVisible = false
        binding.puzzleSquare5.isVisible = false
        binding.puzzleSquare6.isVisible = false
        binding.puzzleSquare7.isVisible = false
        binding.puzzleSquare8.isVisible = false
        binding.puzzleSquare9.isVisible = false
    }

    private fun toggleButtons(enable  : String = "") {
        when (enable) {
            "off" -> {
                binding.navBarCameraButton.also{
                    it.isEnabled = false
                    it.alpha = 0.3F
                }
                binding.savePuzzleButton.also{
                    it.isEnabled = false
                    it.alpha = 0.3F
                }
                binding.navBarImageButton.also{
                    it.isEnabled = false
                    it.alpha = 0.3F
                }
                binding.navBarUserButton.also{
                    it.isEnabled = false
                    it.alpha = 0.3F
                }
            }
            "on" -> {
                binding.navBarCameraButton.also{
                    it.isEnabled = true
                    it.alpha = 1F
                }
                binding.savePuzzleButton.also{
                    it.isEnabled = true
                    it.alpha = 1F
                }
                binding.navBarImageButton.also{
                    it.isEnabled = true
                    it.alpha = 1F
                }
                binding.navBarUserButton.also{
                    it.isEnabled = true
                    it.alpha = 1F
                }
            }
            else -> {
                binding.navBarCameraButton.also{
                    it.isEnabled = !it.isEnabled
                    it.alpha = if (it.isEnabled) 1F else .3f
                }
                binding.savePuzzleButton.also{
                    it.isEnabled = !it.isEnabled
                    it.alpha = if (it.isEnabled) 1F else .3f
                }
                binding.navBarImageButton.also{
                    it.isEnabled = !it.isEnabled
                    it.alpha = if (it.isEnabled) 1F else .3f
                }
                binding.navBarUserButton.also{
                    it.isEnabled = !it.isEnabled
                    it.alpha = if (it.isEnabled) 1F else .3f
                }
                binding.puzzlifyButton.also{
                    it.isEnabled = !it.isEnabled
                    it.alpha = if (it.isEnabled) 1F else .3f
            }
        }
    }

}

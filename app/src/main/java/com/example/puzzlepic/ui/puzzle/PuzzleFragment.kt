@file:Suppress("DEPRECATION")
package com.example.puzzlepic.ui.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.puzzlepic.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.puzzle_fragment.*


class PuzzleFragment : Fragment() {

    lateinit var randomUrl: String
    lateinit var navController: NavController

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

}
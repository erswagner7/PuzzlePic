package com.example.puzzlepic

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import com.example.puzzlepic.ui.main.EventFragment
import com.example.puzzlepic.ui.main.MainFragment
import com.example.puzzlepic.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var  detector: GestureDetectorCompat
    private lateinit var eventFragment: EventFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        eventFragment = EventFragment.newInstance()
        mainFragment  = MainFragment.newInstance()
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commitNow()
            activeFragment = mainFragment
        }
        detector = GestureDetectorCompat(this, DiaryGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
       return if (detector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class DiaryGestureListener: GestureDetector.SimpleOnGestureListener(){
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?:0.0F
            var diffY = moveEvent?.x?.minus(downEvent!!.y) ?:0.0F

            return if (Math.abs(diffX) > Math.abs(diffY)){
                //this is a left or right swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                    if(diffX> 0){
                        //this is a right swipe
                        this@MainActivity.onSwipeRight()
                    }else {
                        //left swipe.
                        this@MainActivity.onLeftSwipe()
                    }
                    true
                }else{
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }else{
                //this is a bottom or a top swipe.
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                    if(diffY > 0){
                        this@MainActivity.onSwipeTop()
                    }else{
                        this@MainActivity.onSwipeBottom()

                    }
                    true
                    }else{
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }
        }
    }
//Text in green are subject to change. Which ever makes sense to majority.
    private fun onSwipeBottom() {
        Toast.makeText(this, "Bottom Swipe", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeTop() {
        Toast.makeText(this, "Top Swipe", Toast.LENGTH_LONG).show()
    }

    private fun onLeftSwipe() {
        if(activeFragment == mainFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, eventFragment)
                .commitNow()
            activeFragment = mainFragment
        }

    }

    private fun onSwipeRight() {
        if (activeFragment == eventFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
            activeFragment = mainFragment
        }
    }
}
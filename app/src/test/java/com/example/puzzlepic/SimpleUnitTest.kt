package com.example.puzzlepic

import com.example.puzzlepic.dto.Picture
import com.example.puzzlepic.dto.UnsplashPhotoUrls
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SimpleUnitTest {

    // Leave this test in until circleci is working
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun confirmDefaultUnsplashURLisPopulated() {
        val emptyString = ""
        val testURL = UnsplashPhotoUrls()
        assert( testURL.raw === emptyString &&
                testURL.full === emptyString &&
                testURL.regular === emptyString &&
                testURL.small === emptyString &&
                testURL.thumb === emptyString)
    }

    @Test
    fun confirmDefaultPhotoURL_returnsDefaultString() {
        val picture = Picture()
        assertEquals(": ", picture.toString())
    }

    @Test
    fun confirmPopulatedPhotoURL_returnsPopulatedString() {
        val testURL = UnsplashPhotoUrls()
        val picture = Picture(
            "testID",
            "testTitle",
            "Jan 1 1970",
            "testLocation",
            "testNotes",
            123, testURL)
        assertEquals("testTitle: Jan 1 1970", picture.toString())
    }
}
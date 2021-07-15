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
    fun testPictureDTO() {
        confirmPopulatedPhotoURL_returnsPopulatedString()

    }

    @Test
    fun confirmPopulatedPhotoURL_returnsPopulatedString() {
        confirmPhotoURL_returnsPhotoURL()
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

    @Test
    fun confirmDefaultPhotoURL_returnsDefualtString() {
        confirmPhotoURL_returnsPhotoURL()
        val picture = Picture()
        assertEquals(": ", picture.toString())
    }

    @Test
    fun confirmPhotoURL_returnsPhotoURL() {
        val testURL = UnsplashPhotoUrls()
        assert(testURL !== null)
        assert(testURL is UnsplashPhotoUrls)
        assert(defaultUnsplashURLisPopulated(testURL))
    }

    private fun defaultUnsplashURLisPopulated(testURL: UnsplashPhotoUrls): Boolean {
        val EMPTY_STRING = ""
        return (testURL.raw === EMPTY_STRING &&
                testURL.full === EMPTY_STRING &&
                testURL.regular === EMPTY_STRING &&
                testURL.small === EMPTY_STRING &&
                testURL.thumb === EMPTY_STRING)
    }
}
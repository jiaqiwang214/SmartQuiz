package com.example.smartquiz

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smartquiz.data.repository.HtmlDecoder
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HtmlDecoderTest {

    @Test
    fun decodesAmpersand() {
        assertEquals(
            "Tom & Jerry",
            HtmlDecoder.decode("Tom &amp; Jerry")
        )
    }

    @Test
    fun decodesQuotationMarks() {
        assertEquals(
            "\"Hello\"",
            HtmlDecoder.decode("&quot;Hello&quot;")
        )
    }

    @Test
    fun decodesApostrophe() {
        assertEquals(
            "It's correct",
            HtmlDecoder.decode("It&#039;s correct")
        )
    }

    @Test
    fun decodesLessThanSymbol() {
        assertEquals(
            "5 < 10",
            HtmlDecoder.decode("5 &lt; 10")
        )
    }

    @Test
    fun decodesGreaterThanSymbol() {
        assertEquals(
            "A > B",
            HtmlDecoder.decode("A &gt; B")
        )
    }

    @Test
    fun plainTextRemainsUnchanged() {
        assertEquals(
            "What is the capital of France?",
            HtmlDecoder.decode(
                "What is the capital of France?"
            )
        )
    }
}

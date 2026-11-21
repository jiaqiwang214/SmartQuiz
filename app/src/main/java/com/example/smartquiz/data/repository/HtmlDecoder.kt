package com.example.smartquiz.data.repository

import android.text.Html

object HtmlDecoder {

    fun decode(value: String): String {
        return Html.fromHtml(
            value,
            Html.FROM_HTML_MODE_LEGACY
        ).toString()
    }
}

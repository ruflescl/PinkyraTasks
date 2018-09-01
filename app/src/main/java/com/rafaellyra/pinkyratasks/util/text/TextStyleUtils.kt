package com.rafaellyra.pinkyratasks.util.text

import android.text.Spannable
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan

class TextStyleUtils {
    companion object {
        val LARGE_TEXT_SIZE = RelativeSizeSpan(2.0f)

        fun strikeThrough(spannable: Spannable, start: Int = 0, end: Int = spannable.length - 1) {
            spannable.setSpan(StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        fun enlarge(spannable: Spannable, start: Int = 0, end: Int = spannable.length - 1) {
            spannable.setSpan(LARGE_TEXT_SIZE, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        fun enlargeFirstWord(spannable: Spannable) {
            val start = 0
            val end = spannable.indexOfFirst { char -> char.equals(' ') }

            enlarge(spannable, start, end)
        }
    }
}

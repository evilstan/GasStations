package com.example.gasstations.presentation

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


class MoneyFuelInputFilter : InputFilter {
    private val pattern: Pattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?")

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val result = (dest.subSequence(0, dstart)
            .toString() + source.toString()
                + dest.subSequence(dend, dest.length))
        val matcher: Matcher = pattern.matcher(result)
        return if (!matcher.matches()) dest.subSequence(dstart, dend) else null
    }
}
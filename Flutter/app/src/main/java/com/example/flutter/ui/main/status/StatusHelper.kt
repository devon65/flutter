package com.example.flutter.ui.main.status

import android.text.SpannableString
import android.text.Spanned
import com.example.flutter.utils.Constants
import java.util.regex.Pattern


object StatusHelper {

    fun setHashtags(message: SpannableString, clickListener: ClickableLink.ClickableLinkListener) {

        val pattern = Pattern.compile(Constants.HASHTAG + "\\w+")
        val matcher = pattern.matcher(message)

        while (matcher.find()) {
            val linkText = message.substring(matcher.start(), matcher.end())
            message.setSpan(
                ClickableHashtag(
                    linkText,
                    clickListener
                ), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun setUserMentions(message: SpannableString, clickListener: ClickableLink.ClickableLinkListener, userId: String? = null) {

        val pattern = Pattern.compile(Constants.CALLOUT + "\\w+")
        val matcher = pattern.matcher(message)

        while (matcher.find()) {
            val linkText = message.substring(matcher.start(), matcher.end())
            message.setSpan(
                ClickableAlias(
                    linkText,
                    clickListener,
                    userId
                ), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun findAllHashtags(message: String): List<String>{
        return findAllSymbolOccurrences(message, Constants.HASHTAG)
    }

    fun findAllUserMentions(message: String): List<String>{
        return findAllSymbolOccurrences(message, Constants.CALLOUT)
    }

    private fun findAllSymbolOccurrences(message: String, symbol: String): List<String> {
        val symbolList = ArrayList<String>()

        val pattern = Pattern.compile(symbol + "\\w+")
        val matcher = pattern.matcher(message)

        while (matcher.find()) {
            symbolList.add(message.substring(matcher.start(), matcher.end()))
        }

        return symbolList
    }
}
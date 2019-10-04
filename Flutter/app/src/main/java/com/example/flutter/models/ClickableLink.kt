package com.example.flutter.models
//
//Adapted from Ankita's code: https://codesinandroid.blogspot.com/2015/08/making-hashtag-and-usertag-clickable-to.html
//
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import android.text.Spanned
import android.widget.TextView
import android.text.TextPaint
import androidx.core.content.ContextCompat.getColor
import com.example.flutter.R
import com.example.flutter.Utils.Constants


open class ClickableLink(val linkText: String, val clickListener: ClickableLinkListener? = null): ClickableSpan() {

    interface ClickableLinkListener{
        fun onLinkClicked(linkText: String, id: String? = null)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
    }

    override fun onClick(widget: View) {
    }
}
package com.example.flutter.ui.main.status
//
//Adapted from Ankita's code: https://codesinandroid.blogspot.com/2015/08/making-hashtag-and-usertag-clickable-to.html
//
import android.text.style.ClickableSpan
import android.view.View
import android.text.TextPaint
import com.example.flutter.R
import com.example.flutter.utils.BlueBird


open class ClickableLink(val linkText: String, val clickListener: ClickableLinkListener? = null): ClickableSpan() {

    interface ClickableLinkListener{
        fun onLinkClicked(linkText: String, id: String? = null)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = BlueBird.instance.getColor(R.color.hyperlinkBlue)
    }

    override fun onClick(widget: View) {
    }
}
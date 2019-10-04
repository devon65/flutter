package com.example.flutter.models

import android.content.Context
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class URL(linkText: String ,clickListener: ClickableLinkListener? = null) : ClickableLink(linkText ,clickListener) {

    override fun onClick(widget: View) {
        clickListener?.onLinkClicked(linkText)
    }
}
package com.example.flutter.ui.main.status

import android.view.View

class ClickableURL(linkText: String, clickListener: ClickableLinkListener? = null) : ClickableLink(linkText ,clickListener) {

    override fun onClick(widget: View) {
        clickListener?.onLinkClicked(linkText)
    }
}
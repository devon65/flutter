package com.example.flutter.ui.main.status

import android.view.View

class ClickableAlias(linkText: String, clickListener: ClickableLinkListener? = null, val userId: String? = null) :
    ClickableLink(linkText, clickListener) {

    override fun onClick(widget: View) {
        val adjustedUserId = userId
        clickListener?.onLinkClicked(linkText, adjustedUserId)
    }
}
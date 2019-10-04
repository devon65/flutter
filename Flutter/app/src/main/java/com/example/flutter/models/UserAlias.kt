package com.example.flutter.models

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

class UserAlias(linkText: String, clickListener: ClickableLinkListener? = null, val userId: String? = null) :
    ClickableLink(linkText, clickListener) {

    override fun onClick(widget: View) {
        clickListener?.onLinkClicked(linkText, userId)
    }
}
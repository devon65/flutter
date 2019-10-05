package com.example.flutter.models
import android.view.View


class Hashtag(linkText: String ,clickListener: ClickableLinkListener? = null): ClickableLink(linkText, clickListener) {

    override fun onClick(widget: View) {
        clickListener?.onLinkClicked(linkText)
    }
}
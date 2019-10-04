package com.example.flutter.models
import android.view.View


class Hashtag(linkText: String ,clickListener: ClickableLinkListener? = null): ClickableLink(linkText, clickListener) {
    init {
        val sup = "ahhh1"
    }

    override fun onClick(widget: View) {
        clickListener?.onLinkClicked(linkText)
    }
}
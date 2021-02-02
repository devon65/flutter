package com.example.flutter.ui.main.status
import android.view.View


class ClickableHashtag(linkText: String, clickListener: ClickableLinkListener? = null): ClickableLink(linkText, clickListener) {

    override fun onClick(widget: View) {
        clickListener?.onLinkClicked(linkText)
    }
}
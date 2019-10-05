package com.example.flutter.ui.main.status

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.models.ClickableLink.ClickableLinkListener
import com.example.flutter.models.Status
import com.example.flutter.ui.main.status.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class StatusRecyclerViewAdapter(
    private val mValues: List<Status>,
    private val statusInteractionListener: OnStatusInteractionListener?) :
    RecyclerView.Adapter<StatusRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Status
            statusInteractionListener?.launchStatusView(item)
        }
    }

    private val hashtagListener = object: ClickableLinkListener {
        override fun onLinkClicked(linkText: String, id: String?) {
            statusInteractionListener?.launchHashtagFeed(linkText)
        }
    }
    private val userMentionListener = object: ClickableLinkListener {
        override fun onLinkClicked(linkText: String, id: String?) {
            statusInteractionListener?.launchUserStory(linkText, id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_status, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status = mValues[position]

        val linkifiedMessage = SpannableString(status.messageBody)

        StatusHelper.setHashtags(linkifiedMessage, hashtagListener)
        StatusHelper.setUserMentions(linkifiedMessage, userMentionListener)
        holder.messageBody.text = linkifiedMessage
        holder.messageBody.movementMethod = LinkMovementMethod.getInstance()

        val handleText = SpannableString(status.user.alias)
        StatusHelper.setUserMentions(handleText, userMentionListener, status.user.userId)

        holder.handle.text = handleText
        holder.handle.movementMethod = LinkMovementMethod.getInstance()
//        with(holder.mView) {
//            tag = status
//            setOnClickListener(mOnClickListener)
//        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val messageBody: TextView = mView.findViewById(R.id.status_message_body)
        val handle: TextView = mView.findViewById(R.id.status_handle)
        val profilePic: ImageView = mView.findViewById(R.id.status_profile_pic)
    }
}

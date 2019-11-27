package com.example.flutter.ui.main.status

import android.text.SpannableString
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.ui.main.status.ClickableLink.ClickableLinkListener
import com.example.flutter.models.Status
import com.example.flutter.utils.Constants
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class StatusRecyclerViewAdapter(
    private val mValues: MutableList<Status>,
    private val statusListListener: OnStatusInteractionListener?,
    private val onFetchStatusesListener: OnFetchStatusesListener) :
    RecyclerView.Adapter<StatusRecyclerViewAdapter.ViewHolder>() {

    interface OnFetchStatusesListener {
        fun fetchMoreStatuses(status: Status)
    }


    private val mOnStatusClickListener: View.OnClickListener

    init {
        mOnStatusClickListener = View.OnClickListener { v ->
            if (v.tag != null) {
                val item = v.tag as Status
                statusListListener?.launchStatusView(item)
            }
        }
    }

    private val hashtagListener = object: ClickableLinkListener {
        override fun onLinkClicked(linkText: String, id: String?) {
            statusListListener?.launchHashtagFeed(linkText)
        }
    }
    private val userMentionListener = object: ClickableLinkListener {
        override fun onLinkClicked(linkText: String, id: String?) {
            statusListListener?.launchUserStory(linkText, id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_status, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlobalScope.launch (IO){
            if(position == mValues.lastIndex) {
                onFetchStatusesListener.fetchMoreStatuses(mValues[position])
            }
        }
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

        val date = Date(status.timeStamp * Constants.MILLISEC_IN_SEC)
        holder.statusTimestamp.text = DateFormat.format("MM-dd-yyyy hh:mm a", date)

        with(holder.profilePic) {
            settings.setLoadWithOverviewMode(true)
            settings.setUseWideViewPort(true)
            loadUrl(status.user.profilePicUrl)
        }

        if(status.attachmentUrl != null) {
            with(holder.statusAttachment) {
                tag = status
                visibility = View.VISIBLE
                settings.setLoadWithOverviewMode(true)
                settings.setUseWideViewPort(true)
                loadUrl(status.attachmentUrl)
//                setOnClickListener(mOnStatusClickListener)
            }
        }

        with(holder.statusLayout) {
            tag = status
            setOnClickListener(mOnStatusClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val messageBody: TextView = mView.findViewById(R.id.status_message_body)
        val handle: TextView = mView.findViewById(R.id.status_handle)
        val statusTimestamp: TextView = mView.findViewById(R.id.status_date)
        val profilePic: WebView = mView.findViewById(R.id.status_profile_pic)
        val statusLayout: LinearLayout = mView.findViewById(R.id.status_layout)
        val statusAttachment: WebView = mView.findViewById(R.id.status_attachment)
    }

    companion object{
        const val STORY = "story"
        const val FEED = "feed"
    }
}

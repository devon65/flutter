package com.example.flutter.ui.main.status

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.models.Status
import com.example.flutter.ui.main.feed.HashtagFeedActivity
import com.example.flutter.ui.main.story.UserStoryActivity

class StatusViewActivity : AppCompatActivity(), StatusContract.IStatusActivity {

    internal lateinit var presenter: StatusContract.IStatusPresenter

    private val hashtagListener = object: ClickableLink.ClickableLinkListener {
        override fun onLinkClicked(linkText: String, id: String?) {
            launchHashtagFeed(linkText)
        }
    }
    private val userMentionListener = object: ClickableLink.ClickableLinkListener {
        override fun onLinkClicked(linkText: String, id: String?) {
            launchUserStory(linkText, id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_view)
        setPresenter(StatusPresenter(this))

        val status = presenter.getStatus(intent.getStringExtra(STATUS_ID))

        val messageBody: TextView = findViewById(R.id.status_message_body)
        val linkifiedMessage = SpannableString(status?.messageBody ?: "")
        StatusHelper.setHashtags(linkifiedMessage, hashtagListener)
        StatusHelper.setUserMentions(linkifiedMessage, userMentionListener)
        messageBody.text = linkifiedMessage
        messageBody.movementMethod = LinkMovementMethod.getInstance()

        val handle: TextView = findViewById(R.id.status_handle)
        val handleText = SpannableString(status?.user?.alias ?: "")
        StatusHelper.setUserMentions(handleText, userMentionListener, status?.user?.userId)
        handle.text = handleText
        handle.movementMethod = LinkMovementMethod.getInstance()

        val profilePic: ImageView = findViewById(R.id.status_profile_pic)

    }

    override fun setPresenter(presenter: StatusContract.IStatusPresenter) {
        this.presenter = presenter
    }

    override fun launchHashtagFeed(hashtagText: String) {
        val intent = Intent(this, HashtagFeedActivity::class.java).apply {
            putExtra(HashtagFeedActivity.HASHTAG_ID, hashtagText)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }

    override fun launchUserStory(userMentionText: String, userId: String?) {
        if (userId != null) {
            val intent = Intent(this, UserStoryActivity::class.java).apply {
                putExtra(UserStoryActivity.USER_ID, userId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }

    override fun launchStatusView(status: Status) {
        //Already here. Do nothing
    }

    companion object {
        const val STATUS_ID = "statusId"
    }
}

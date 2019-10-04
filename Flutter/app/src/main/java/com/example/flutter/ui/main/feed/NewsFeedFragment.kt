package com.example.flutter.ui.main.feed

import android.content.Context
import android.os.Bundle
import android.se.omapi.Session
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flutter.R
import com.example.flutter.Utils.SessionInfo
import com.example.flutter.models.Hashtag
import com.example.flutter.models.Status
import com.example.flutter.ui.main.status.OnStatusInteractionListener

import com.example.flutter.ui.main.status.dummy.DummyContent
import com.example.flutter.ui.main.status.dummy.DummyContent.DummyItem
import com.example.flutter.ui.main.status.StatusRecyclerViewAdapter

class NewsFeedFragment : Fragment(), OnStatusInteractionListener {

    private var hashtag: String? = null
    private var listener: OnNewsFeedInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            hashtag = it.getString(HASHTAG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_feed, container, false)

        val statusList = view.findViewById<RecyclerView>(R.id.feed_status_list)
        val feedList = if (hashtag != null) SessionInfo.getStatusesByHashtag(hashtag?: "")
                        else SessionInfo.getUserFeed(SessionInfo.currentUser)
        // Set the adapter
        with(statusList) {
            layoutManager = LinearLayoutManager(context)
            adapter = StatusRecyclerViewAdapter(feedList, listener)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNewsFeedInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnNewsFeedInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnNewsFeedInteractionListener: OnStatusInteractionListener

    override fun onHashtagClicked(hashtagText: String) {
        listener?.onHashtagClicked(hashtagText)
    }

    override fun onUserMentionClicked(userMentionText: String, userId: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusClicked(status: Status) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        const val HASHTAG_ID = "hashtagId"

        @JvmStatic
        fun newInstance(hashtag: String? = null) =
            NewsFeedFragment().apply {
                arguments = Bundle().apply {
                    putString(HASHTAG_ID, hashtag)
                }
            }
    }
}

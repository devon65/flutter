package com.example.flutter.ui.main.feed

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.flutter.R
import com.example.flutter.models.Status
import com.example.flutter.ui.main.status.OnStatusInteractionListener

import com.example.flutter.ui.main.status.StatusRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_status_list.*

class NewsFeedFragment : Fragment() {

    private var hashtag: String? = null
    private var listener: OnNewsFeedInteractionListener? = null
    private val feedList: ArrayList<Status> = ArrayList()

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
        listener?.getStatusFeedList(hashtag,
            {feedList.addAll(it)
            statusList.adapter?.notifyDataSetChanged()},
            { Toast.makeText(context, getText(R.string.status_could_not_retrieve_next_page), Toast.LENGTH_LONG).show() })

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

    interface OnNewsFeedInteractionListener: OnStatusInteractionListener {
        fun getStatusFeedList(hashtagText: String?, onSuccess: (List<Status>) -> Unit, onFailure: () -> Unit)
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

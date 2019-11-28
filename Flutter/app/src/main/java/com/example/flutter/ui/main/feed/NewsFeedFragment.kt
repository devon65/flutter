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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class NewsFeedFragment : Fragment(), StatusRecyclerViewAdapter.OnFetchStatusesListener {

    private var hashtag: String? = null
    private var loadUponCreation: Boolean? = null

    private var listener: OnNewsFeedInteractionListener? = null
    private val feedList: ArrayList<Status> = ArrayList()
    private lateinit var statusListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            hashtag = it.getString(HASHTAG_ID)
            loadUponCreation = it.getBoolean(LOAD_UPON_CREATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_feed, container, false)

        statusListView = view.findViewById(R.id.feed_status_list)

        if (loadUponCreation == true){
            loadNewsFeed()
        }
        return view
    }

    fun loadNewsFeed(){
        listener?.getStatusFeedList(hashtag, { appendStatuses(it) }, { showFailedToGetStatuses() })

        // Set the adapter
        with(statusListView) {
            layoutManager = LinearLayoutManager(context)
            adapter = StatusRecyclerViewAdapter(feedList, listener, this@NewsFeedFragment)
        }
    }

    fun clearStatuses(){
        feedList.clear()
        statusListView.adapter?.notifyDataSetChanged()
    }

    private fun appendStatuses(statuses: List<Status>){
        val startIndex = if(feedList.isEmpty()) 0
                         else feedList.lastIndex + 1
        feedList.addAll(statuses)
        statusListView.adapter?.notifyItemRangeChanged(startIndex, statuses.size)
    }

    private fun showFailedToGetStatuses(){
//        Toast.makeText(context, getText(R.string.status_could_not_retrieve_next_page), Toast.LENGTH_LONG).show()
    }

    override fun fetchMoreStatuses(status: Status) {
        GlobalScope.launch (Dispatchers.IO){
            listener?.getStatusFeedList(hashtag, {appendStatuses(it)}, {showFailedToGetStatuses()}, status)
        }
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
        fun getStatusFeedList(
            hashtagText: String?,
            onSuccess: (List<Status>) -> Unit,
            onFailure: () -> Unit,
            status: Status? = null
        )
    }

    companion object {

        const val HASHTAG_ID = "hashtagId"
        const val LOAD_UPON_CREATION = "loadUponCreation"

        @JvmStatic
        fun newInstance(hashtag: String? = null, loadUponCreation: Boolean = false) =
            NewsFeedFragment().apply {
                arguments = Bundle().apply {
                    putString(HASHTAG_ID, hashtag)
                    putBoolean(LOAD_UPON_CREATION, loadUponCreation)
                }
            }
    }
}

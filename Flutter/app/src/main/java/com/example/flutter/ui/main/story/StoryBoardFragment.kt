package com.example.flutter.ui.main.story

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flutter.R
import com.example.flutter.Utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.ui.main.status.OnStatusInteractionListener
import com.example.flutter.ui.main.status.StatusRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_story_board.*

/**
 * A placeholder fragment containing a simple view.
 */
class StoryBoardFragment : Fragment() {

    private var displayedUser: User? = null
    private var listener: OnStoryBoardInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val userId = it.getString(USER_ID)
            this.displayedUser = listener?.getUser(userId)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_story_board, container, false)

        val userName = view.findViewById<TextView>(R.id.user_name)
        userName.text = displayedUser?.name
        val userAlias = view.findViewById<TextView>(R.id.user_alias)
        userAlias.text = displayedUser?.alias

        val statusList = view.findViewById<RecyclerView>(R.id.story_status_list)
        val storyFeed = listener?.getUserStory(displayedUser) ?: listOf()
        // Set the adapter
        with(statusList) {
            layoutManager = LinearLayoutManager(context)
            adapter = StatusRecyclerViewAdapter(storyFeed, listener)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStoryBoardInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnStoryBoardInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnStoryBoardInteractionListener: OnStatusInteractionListener {
        fun getUser(userId: String?): User?
        fun getUserStory(user: User?): List<Status>
    }

    companion object {
        private const val USER_ID = "userId"

        @JvmStatic
        fun newInstance(userId: String? = null): StoryBoardFragment {
            return StoryBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                }
            }
        }
    }
}
package com.example.flutter.ui.main.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flutter.R
import com.example.flutter.ui.main.PageViewModel
import com.example.flutter.ui.main.feed.NewsFeedFragment
import com.example.flutter.ui.main.feed.StatusRecyclerViewAdapter
import com.example.flutter.ui.main.feed.dummy.DummyContent

/**
 * A placeholder fragment containing a simple view.
 */
class StoryBoardFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: NewsFeedFragment.OnListFragmentInteractionListener? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_story_board, container, false)

        val statusList = view.findViewById<RecyclerView>(R.id.story_status_list)
        // Set the adapter
        with(statusList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = StatusRecyclerViewAdapter(DummyContent.ITEMS, listener)
        }

        return view
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyContent.DummyItem?)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): StoryBoardFragment {
            return StoryBoardFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
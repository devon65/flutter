package com.example.flutter.ui.main.personlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flutter.R
import com.example.flutter.models.User

class PersonListFragment : Fragment() {
    private lateinit var userIdList: List<String>

    private var listener: PersonListFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            userIdList = it.getStringArrayList(USER_ID_LIST) ?: listOf()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_personview_list, container, false)
        val userList = listener?.getUserListByIdList(userIdList) ?: listOf()
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = PersonListRecyclerViewAdapter(userList, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PersonListFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement PersonListFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface PersonListFragmentListener {
        fun onPersonClicked(person: User?)
        fun getUserListByIdList(idList: List<String>): List<User>
    }

    companion object {
        const val USER_ID_LIST = "userIdList"

        @JvmStatic
        fun newInstance(userIdList: ArrayList<String>) =
            PersonListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(USER_ID_LIST, userIdList)
                }
            }
    }
}

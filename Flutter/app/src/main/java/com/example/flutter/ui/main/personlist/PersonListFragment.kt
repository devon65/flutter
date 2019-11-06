package com.example.flutter.ui.main.personlist

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
import com.example.flutter.models.User

class PersonListFragment : Fragment(), PersonListRecyclerViewAdapter.OnFetchMorePeopleListener {
    private var userList = ArrayList<User>()
    private lateinit var userRecyclerView: RecyclerView

    private var listener: PersonListFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            userIdList = it.getStringArrayList(USER_ID_LIST) ?: listOf()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_personview_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            userRecyclerView = view
            with(userRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = PersonListRecyclerViewAdapter(userList, listener, this@PersonListFragment)
            }
        }

        listener?.getPersonList({ appendStatuses(it) }, { showFailedToGetUsers() })
        return view
    }

    private fun appendStatuses(users: List<User>){
        val startIndex = if(userList.isEmpty()) 0
                        else userList.lastIndex + 1
        userList.addAll(users)
        userRecyclerView.adapter?.notifyItemRangeChanged(startIndex, users.size)
    }

    private fun showFailedToGetUsers(){
        Toast.makeText(context, getText(R.string.user_could_not_retrieve_next_page), Toast.LENGTH_LONG).show()
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

    override fun fetchMorePeople(nextIndex: Int) {

    }

    interface PersonListFragmentListener {
        fun onPersonClicked(person: User?)
        fun getPersonList(onSuccess: (List<User>) -> Unit, onFailure: () -> Unit)
    }

    companion object {
        const val USER_ID_LIST = "userIdList"

        @JvmStatic
        fun newInstance() =
            PersonListFragment().apply {
//                arguments = Bundle().apply {
//                    putStringArrayList(USER_ID_LIST, userIdList)
//                }
            }
    }
}

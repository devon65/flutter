package com.example.flutter.ui.main.personlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.models.User


class PersonListRecyclerViewAdapter(
    private val mValues: List<User>,
    private val mListener: PersonListFragment.PersonListFragmentListener?
) : RecyclerView.Adapter<PersonListRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val person = v.tag as User
            mListener?.onPersonClicked(person)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_personview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = mValues[position]
        holder.nameOfUser.text = person.name
        holder.userAlias.text = person.alias
//        holder.profilePic.background =

        with(holder.mView) {
            tag = person
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val profilePic: ImageView = mView.findViewById(R.id.person_profile_pic)
        val nameOfUser: TextView = mView.findViewById(R.id.person_name)
        val userAlias: TextView = mView.findViewById(R.id.person_alias)

//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
    }
}

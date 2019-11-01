package com.example.flutter.ui.main.personlist

import com.example.flutter.Utils.Constants
import com.example.flutter.Utils.SessionInfo
import com.example.flutter.models.User

class PersonListPresenter(personListActivity: PersonListContract.IPersonListActivity): PersonListContract.IPersonListPresenter {

    private var personListActivity: PersonListContract.IPersonListActivity? = personListActivity

    override fun getPersonList(personListType: String, userId: String): List<User> {
        return when(personListType){
            Constants.FOLLOWERS -> SessionInfo.getUserFollowers(userId)
            Constants.USERS_FOLLOWED -> SessionInfo.getPersonsFollowedByUser(userId)
            else -> listOf()
        }
    }

    override fun onDestroy() {
        personListActivity = null
    }
}
package com.example.flutter.ui.main.personlist

import com.example.flutter.utils.Constants
import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.User

class PersonListPresenter(personListActivity: PersonListContract.IPersonListActivity): PersonListContract.IPersonListPresenter {

    private var personListActivity: PersonListContract.IPersonListActivity? = personListActivity

    override fun getPersonList(personListType: String, userId: String, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit) {
        when(personListType){
            Constants.FOLLOWERS -> SessionInfo.getUserFollowers(userId, onSuccess, onFailure)
            Constants.USERS_FOLLOWED -> SessionInfo.getPersonsFollowedByUser(userId, onSuccess, onFailure)
        }
    }

    override fun onDestroy() {
        personListActivity = null
    }
}
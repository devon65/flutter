package com.example.flutter.ui.main.personlist

import com.example.flutter.Utils.SessionInfo
import com.example.flutter.models.User

class PersonListPresenter(personListActivity: PersonListContract.IPersonListActivity): PersonListContract.IPersonListPresenter {

    private var personListActivity: PersonListContract.IPersonListActivity? = personListActivity

    override fun getUserListByIdList(idList: List<String>): List<User> {
        val personList = ArrayList<User>()
        for (personId in idList){
            val person = SessionInfo.getUserById(personId)
            if (person != null) {
                personList.add(person)
            }
        }
        return personList
    }

    override fun onDestroy() {
        personListActivity = null
    }
}
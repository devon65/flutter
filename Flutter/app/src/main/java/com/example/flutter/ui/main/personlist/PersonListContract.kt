package com.example.flutter.ui.main.personlist

import com.example.flutter.BasePresenter
import com.example.flutter.BaseView
import com.example.flutter.models.User

interface PersonListContract {
    interface IPersonListPresenter: BasePresenter{
        fun getPersonList(personListType: String, userId: String, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit)
    }

    interface IPersonListActivity: BaseView<IPersonListPresenter>
}
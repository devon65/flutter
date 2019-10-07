package com.example.flutter.ui.main.personlist

import com.example.flutter.BasePresenter
import com.example.flutter.BaseView
import com.example.flutter.models.User

interface PersonListContract {
    interface IPersonListPresenter: BasePresenter{
        fun getUserListByIdList(idList: List<String>): List<User>
    }

    interface IPersonListActivity: BaseView<IPersonListPresenter>
}
package com.example.flutter.ui.main.status

import com.example.flutter.BasePresenter
import com.example.flutter.BaseStatusesView
import com.example.flutter.models.Status

interface StatusContract {
    interface IStatusPresenter : BasePresenter {
        fun getStatus(statusId: String?): Status?
    }

    interface IStatusActivity : BaseStatusesView<IStatusPresenter> {

    }
}
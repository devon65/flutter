package com.example.flutter.ui.main.status

import com.example.flutter.utils.SessionInfo
import com.example.flutter.models.Status

class StatusPresenter(statusActivity: StatusContract.IStatusActivity): StatusContract.IStatusPresenter {

    private var statusActivity: StatusContract.IStatusActivity? = statusActivity

    override fun getStatus(statusId: String?): Status? {
        return SessionInfo.getStatusById(statusId)
    }

    override fun onDestroy() {
        this.statusActivity = null
    }
}
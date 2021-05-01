package com.assessment.ui.details

import com.assessment.base.activity.BaseActivityView
import com.assessment.base.activity.BaseActivityRouter

class DetailsContract {

    interface View: BaseActivityView.View<Presenter>{
        fun loadWebView(url: String)
    }

    abstract class Presenter: BaseActivityView.Presenter<View, BaseActivityRouter>() {
    }
}
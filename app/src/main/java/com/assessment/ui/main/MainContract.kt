package com.assessment.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.assessment.base.activity.BaseActivityView
import com.assessment.base.activity.BaseActivityRouter

class MainContract {

    interface View: BaseActivityView.View<Presenter>{
        fun setNewsAdapter(adapter: RecyclerView.Adapter<*>)
        fun setError()
    }

    abstract class Presenter: BaseActivityView.Presenter<View, BaseActivityRouter>() {
        abstract fun openDetailsActivity(url :String?, title :String?)
    }
}
package com.assessment.ui.main.adapter

import android.view.View
import com.assessment.R
import com.assessment.base.recycler.BaseAdapterDelegate
import com.assessment.data.models.response.NewsResults

class MainAdapterDelegate(private val clickAction: (Int) -> Unit): BaseAdapterDelegate<NewsResults, MainAdapterViewHolder>() {

    override fun isForViewType(items: NewsResults, position: Int): Boolean {
        return true;
    }

    override fun getHolderLayoutRes(): Int = R.layout.row_item_main

    override fun getHolder(inflatedView: View): MainAdapterViewHolder = MainAdapterViewHolder(inflatedView, clickAction)
}
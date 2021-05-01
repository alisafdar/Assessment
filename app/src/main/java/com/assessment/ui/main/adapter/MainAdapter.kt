package com.assessment.ui.main.adapter

import com.assessment.base.recycler.BaseRecyclerAdapter
import com.assessment.data.models.response.NewsResults
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager

class MainAdapter : BaseRecyclerAdapter<NewsResults>() {

    var callback: ((Int) -> Unit)? = null

    override fun initDelegatesManager(delegatesManager: AdapterDelegatesManager<NewsResults>) {
        delegatesManager.addDelegate(MainAdapterDelegate { callback?.invoke(it) })
    }
}
package com.assessment.ui.main.adapter

import android.view.View
import android.widget.TextView
import com.assessment.R
import com.assessment.base.recycler.BaseViewHolder
import com.assessment.data.models.response.NewsResults
import com.assessment.utils.click
import com.assessment.utils.find

class MainAdapterViewHolder(val view: View,
                            val clickAction: (Int) -> Unit) : BaseViewHolder<NewsResults>(view) {

    private val titleTextView = find<TextView>(R.id.titleTextView)
    private val byTextView = find<TextView>(R.id.byLineTextView)
    private val sourceTextView = find<TextView>(R.id.sourceTextView)
    private val dateTextView = find<TextView>(R.id.dateTextView)

    init {
        view.click { clickAction(adapterPosition) }
    }

    override fun fillFields() {
        titleTextView.text = item.title
        byTextView.text = item.byline
        sourceTextView.text = item.source
        dateTextView.text = item.published_date
    }
}
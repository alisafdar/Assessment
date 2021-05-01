package com.assessment.ui.main

import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assessment.R
import com.assessment.base.activity.BaseActivity
import com.assessment.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.assessment.di.AppComponent

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun setupUI() {
        setupToolbar()
        setupRecyclers()
    }

    private fun setupRecyclers() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun setupToolbar() {
        super.setupToolbar()
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
            it.setTitle(R.string.main_title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {}
            R.id.menu_dots -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun setNewsAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.visibility = VISIBLE
        recyclerView.adapter = adapter
    }

    override fun setError() {
        recyclerView.visibility = GONE
        noRecordsFoundTextView.visibility = VISIBLE
    }

    override fun replaceFragment(fragment: BaseFragment<*>, addToBackStack: Boolean) {
    }
}
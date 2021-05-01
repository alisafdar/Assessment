package com.assessment.utils

import android.app.Activity
import android.app.Dialog
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

inline fun View.click(crossinline _action: () -> Unit) {
    RxView.clicks(this).throttleFirst(800, TimeUnit.MILLISECONDS).subscribe({ _action() })
}

val View.dm: DisplayMetrics
    get() = resources.displayMetrics


fun Float.pxToDp(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val dp = this / (metrics.densityDpi / 160f)
    return Math.round(dp)
}

fun Float.dpToPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return Math.round(px)
}

fun Int.pxToDp(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val dp = this / (metrics.densityDpi / 160f)
    return Math.round(dp)
}

fun Int.dpToPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return Math.round(px)
}

fun View.dpToPx(dp: Int): Int {
    return (dp * this.dm.density + 0.5).toInt()
}

fun View.pxToDp(px: Int): Int {
    return (px / this.dm.density + 0.5).toInt()
}


fun View.onClick(f: (View) -> Unit) {
    this.setOnClickListener(f)
}

fun View.onLongClick(f: (View) -> Boolean) {
    this.setOnLongClickListener(f)
}

fun View.onTouchEvent(f: (View, MotionEvent) -> Boolean) {
    this.setOnTouchListener(f)
}

fun View.onKeyEvent(f: (View, Int, KeyEvent) -> Boolean) {
    this.setOnKeyListener(f)
}

fun View.onFocusChange(f: (View, Boolean) -> Unit) {
    this.setOnFocusChangeListener(f)
}

fun CompoundButton.onCheckedChanged(f: (CompoundButton, Boolean) -> Unit) {
    this.setOnCheckedChangeListener(f)
}

fun AdapterView<*>.onItemClick(f: (AdapterView<*>, View, Int, Long) -> Unit) {
    this.setOnItemClickListener(f)
}

inline fun <T : AbsListView> T.onScrollChanged(
    crossinline stateChanged: (View, Int) -> Unit
) {
    val listener = object : AbsListView.OnScrollListener {
        override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            stateChanged(view, scrollState)
        }

        override fun onScroll(
            view: AbsListView, firstVisibleItem: Int,
            visibleItemCount: Int, totalItemCount: Int
        ) {
        }
    }
    this.setOnScrollListener(listener)
}

inline fun <reified T : View> RecyclerView.ViewHolder.find(@IdRes res: Int): T =
    itemView.findViewById(res) as T

inline fun <reified T : View> View.find(id: Int): T = this.findViewById(id) as T

inline fun <reified T : View> Dialog.find(id: Int): T = this.findViewById(id) as T

inline fun <reified T : View> Activity.find(id: Int): T = this.findViewById(id) as T

inline fun <reified T : View> Fragment.find(id: Int): T = this.view?.findViewById(id) as T


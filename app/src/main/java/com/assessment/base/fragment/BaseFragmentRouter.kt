package com.assessment.base.fragment

import androidx.annotation.IdRes
import com.assessment.base.BaseRouter

interface BaseFragmentRouter : BaseRouter {

    fun popBackStack()

    fun switchNestedFragment(_fragment: BaseFragment<*>, @IdRes _idContainer: Int, _addToBackStack: Boolean)

     fun <T : BaseFragment<*>> findNestedFragmentById(@IdRes _idContainer: Int): T?
}

package com.assessment.base

import android.os.Bundle
import com.assessment.base.fragment.BaseFragment

interface BaseRouter {

    fun noInternet()

    fun showProgress()

    fun hideProgress()

    fun showKeyboard()

    fun hideKeyboard()

    fun clickBack()

    fun finishActivity()

    fun finishActivityResult(requestCode : Int)

    fun finishActivityResult(requestCode : Int, bundle: Bundle)

    fun replaceFragment(fragment: BaseFragment<*>, addToBackStack: Boolean)

    fun startActivity(_activityClass: Class<*>, _bundle: Bundle?, enableAnim: Boolean)

    fun startActivity(_activityClass: Class<*>, vararg _flag: Int)

    fun startActivityForResult(_activityClass: Class<*>, requestCode: Int, _bundle: Bundle? = null)

}

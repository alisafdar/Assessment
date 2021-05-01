package com.assessment.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResults (val published_date: String?,
                        val source: String?,
                        val byline: String?,
                        val title: String?,
                        val url: String?) : Parcelable

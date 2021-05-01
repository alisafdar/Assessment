package com.assessment.data.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(val results: List<NewsResults>) : Parcelable
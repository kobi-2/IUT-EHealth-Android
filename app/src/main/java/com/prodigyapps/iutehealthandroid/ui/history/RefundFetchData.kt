package com.prodigyapps.iutehealthandroid.ui.history

import android.graphics.Bitmap
import kotlin.properties.Delegates

class RefundFetchData {
    @JvmField
    var bmp: Bitmap? = null

    @JvmField
    var status: String = "Loading"

    @JvmField
    var billNo: Int = 0

    @JvmField
    var amount: String = ""

}
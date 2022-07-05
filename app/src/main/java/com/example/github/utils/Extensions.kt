package com.example.github.utils

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.github.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Fragment.snackBar(message : String){
    Snackbar.make(requireView(),message,Snackbar.LENGTH_LONG).show()
}

fun ImageView.loadCircular(url : String){
    Glide.with(this)
        .load(url) // image url
        .placeholder(R.drawable.user) // any placeholder to load at start
        .error(R.drawable.user)  // any image in case of error
        .circleCrop()
        .into(this)
}

fun String.toFormattedDate() : String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date = dateFormat.parse(this)
    val pattern = "dd MMMM yyyy HH:mm:aa zzzz"
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(date)
}

fun String.dateTimetoText(): String? {
    var convTime: String? = null
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val pasTime = dateFormat.parse(this)
        val nowTime = Date()
        val dateDiff = nowTime.time - pasTime.time
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        if (second < 60) {
            convTime = "$second Seconds "
        } else if (minute < 60) {
            convTime = "$minute Minutes "
        } else if (hour < 24) {
            convTime = "$hour Hours "
        } else if (day >= 7) {
            convTime = if (day > 360) {
                (day / 360).toString() + " Years "
            } else if (day > 30) {
                (day / 30).toString() + " Months "
            } else {
                (day / 7).toString() + " Week "
            }
        } else if (day < 7) {
            convTime = if (day == 1L) "$day Day " else "$day Days "
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        Timber.e("ConvTimeE", e.message)
    }
    return convTime
}

fun String.getError() : String{
    if (this.contains("hostname")){
        return "Please check your internet connection"
    }
    return this
}

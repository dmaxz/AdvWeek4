package com.example.advweek4.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.BindingAdapter
import com.example.advweek4.R
import com.example.advweek4.view.MainActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Observable
import java.lang.Exception

fun ImageView.loadImage( url: String?, progressBar: ProgressBar){
    Picasso.get().load(url).resize(400,400).centerCrop().error(R.drawable.baseline_error_24).into(this, object:Callback{
        override fun onSuccess() {
            progressBar.visibility = View.GONE
        }

        override fun onError(e: Exception?) {
        }
    })

}

@BindingAdapter("android:imageUrl", "android:progressBar")
fun loadPhotoURL(view: ImageView, url: String, pb: ProgressBar){
    view.loadImage(url, pb)
}



fun showNotification(title: String, content:String, icon:Int){
    val channelId = "${MainActivity.instance?.packageName}-${MainActivity.instance?.getString(R.string.app_name)}"
    val notificationBuilder = NotificationCompat.Builder(MainActivity.instance!!.applicationContext, channelId).apply {
        setSmallIcon(icon)
        setContentTitle(title)
        setContentText(content)
        setStyle(NotificationCompat.BigTextStyle())
        priority = NotificationCompat.PRIORITY_DEFAULT
        setAutoCancel(true)
    }
    val notificationManager = NotificationManagerCompat.from(MainActivity.instance!!.applicationContext.applicationContext!!)
    if (ActivityCompat.checkSelfPermission(
            MainActivity.instance!!.applicationContext,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return
    }
    notificationManager.notify(1001, notificationBuilder.build())
}

fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channelId = "${context.packageName}-$name"
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        channel.setShowBadge(showBadge)
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
package com.example.advweek4.view

import android.Manifest
import android.app.NotificationManager
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import com.example.advweek4.R
import com.example.advweek4.util.createNotificationChannel
import com.example.advweek4.util.showNotification
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Notification
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.timer
import io.reactivex.rxjava3.core.Observer

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    init {
        instance = this

    }

    companion object {
        var instance: MainActivity ?= null

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT,false,getString(R.string.app_name), "App notification channel")

        val fab = findViewById<FloatingActionButton>(R.id.fabNotif)
        fab.setOnClickListener {
            val observable = Observable.timer(5, TimeUnit.SECONDS).apply {
                subscribeOn(Schedulers.io())
                observeOn(AndroidSchedulers.mainThread())
                subscribe {
                    Log.wtf("Obsrv_Message", "five seconds")
                    showNotification("Dummy","A new notification created", R.drawable.baseline_mail_24)
                }
            }
        }

        val observable = Observable.just("a stream of data", "hello", "world")
        val observer = object: Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.wtf("Obsvr_Messages", "begin subscribe")
            }

            override fun onError(e: Throwable) {
                Log.wtf("Obsvr_Messages", "error: ${e.message.toString()}")
            }

            override fun onComplete() {
                Log.wtf("Obsvr_Messages", "complete")
            }

            override fun onNext(t: String) {
                Log.wtf("Obsvr_Messages", "data: $t")
            }
        }
//        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer)
        observable.apply {
//            timer(5, TimeUnit.SECONDS)
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            subscribe(observer)
        }

        val data = "hello world"
        val bundle = Bundle()
        bundle.putString("key", data)
//        val navController = findNavController(this, R.id.fragmentHost)
//        navController.setGraph(R.navigation.main_navigation, bundle)
    }
}
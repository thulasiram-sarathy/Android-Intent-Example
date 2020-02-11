package com.thul.androidexampleproject

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var alarmManager: AlarmManager? = null
    var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, AlarmBroadcastReceive::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)

        btnEnd.setOnClickListener {
            cancelNotification()
        }

        btnStart.setOnClickListener {
                sendNotification()

        }
        btnAlarmStart.setOnClickListener { startAlarm() }
        btnAlarmStop.setOnClickListener { cancelAlarm() }
    }

    fun sendNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "DeliveryChannel"
        val channelName: CharSequence = "Delivery and Service"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel: NotificationChannel? = null
            notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(
                /*100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400*/
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }


        val builder = NotificationCompat.Builder(this)
            .setChannelId(channelId)
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://theandroidstudio.in"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        builder.setContentIntent(pendingIntent)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, android.R.drawable.btn_default))
        builder.setContentTitle("App Notification")
        builder.setContentText("Notification Message")
        builder.setSubText("Tap to view.")

        notificationManager.notify(1, builder.build())
    }

    fun cancelNotification() {
        val ns = Context.NOTIFICATION_SERVICE
        val nMgr =
            applicationContext.getSystemService(ns) as NotificationManager
        nMgr.cancel(1)
    }

    private fun startAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager?.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
        } else {
            alarmManager?.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
        }
    }

    private fun cancelAlarm() {
        alarmManager?.cancel(pendingIntent)
        Toast.makeText(applicationContext, "Alarm Cancelled", Toast.LENGTH_LONG).show()
    }

}

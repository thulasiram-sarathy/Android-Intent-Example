package com.thul.androidexampleproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class AlarmBroadcastReceive : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null && intent.action == "android.intent.action.BOOT_COMPLETED") {
            val serviceIntent = Intent(context, AlarmService::class.java)
            context.startService(serviceIntent)
        } else {
            Toast.makeText(
                context.applicationContext,
                "Alarm Manager just ran",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
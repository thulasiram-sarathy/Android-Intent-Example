package com.thul.androidexampleproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sticky.*


class StickyActivity : AppCompatActivity() {

    private val br: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            Toast.makeText(context, "got it..", level).show()
            Log.v("isCharging"," "+ level)
        }
    }
    private var inf: IntentFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky)

        inf = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        inf!!.addAction("com.thul.androidexampleproject.STICK")
        btnOne.setOnClickListener {
                val `in` = Intent()
                `in`.action = "com.thul.androidexampleproject.STICK"
                sendBroadcast(`in`)

        }
        btnTwo.setOnClickListener{
                registerReceiver(br, inf)
        }
    }



}

package com.example.kaveh.smstester

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

@Suppress("DEPRECATION")
class SmsReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle: Bundle? = intent?.extras

        if (bundle != null) {
            val pdus = bundle.get("pdus") as Array<Any>?

            pdus?.let {
                for (pdu in it) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = smsMessage.displayOriginatingAddress
                    val messageBody = smsMessage.messageBody

                    // ارسال اطلاعات به IntentService برای نمایش نوتیفیکیشن
                    val serviceIntent = Intent(context, NotificationService::class.java)
                    serviceIntent.putExtra("sender", sender)
                    serviceIntent.putExtra("messageBody", messageBody)


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context?.startForegroundService(serviceIntent)
                    } else {
                        context?.startService(serviceIntent)
                    }
                }
            }
        }



    }


}

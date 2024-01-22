package com.example.kaveh.smstester

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>
                for (i in pdus.indices) {
                    val currentMessage = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                    val senderNumber = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody

                    // انجام عملیات مربوط به پیام دریافتی
                    Log.d("SmsReceiver", "Sender: $senderNumber, Message: $message")
                }
            }
        }
    }
}

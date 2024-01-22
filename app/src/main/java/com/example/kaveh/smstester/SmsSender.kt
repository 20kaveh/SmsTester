package com.example.kaveh.smstester

import android.telephony.SmsManager

class SmsSender {
    fun sendSms(phoneNumber: String, message: String) {
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }
}
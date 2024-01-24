package com.example.kaveh.smstester
import android.annotation.SuppressLint
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationService : IntentService("NotificationService") {
    @SuppressLint("ForegroundServiceType")
    override fun onHandleIntent(intent: Intent?) {
        // اطلاعات برای نمایش نوتیفیکیشن
        val sender = intent?.getStringExtra("sender")
        val messageBody = intent?.getStringExtra("messageBody")

        // ایجاد Intent برای باز کردن اپلیکیشن در صورت کلیک روی نوتیفیکیشن
        val resultIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_IMMUTABLE)

        // ایجاد نوتیفیکیشن
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "Your_Channel_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("پیام جدید از $sender")
            .setContentText(messageBody)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // ارسال نوتیفیکیشن
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_Channel_ID"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
            startForeground(1, builder.build())
        }

        notificationManager.notify(0, builder.build())
    }
}

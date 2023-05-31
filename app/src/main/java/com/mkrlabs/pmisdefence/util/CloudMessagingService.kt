package com.mkrlabs.pmisdefence.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.ui.HomeActivity
import java.net.URL

class CloudMessagingService : FirebaseMessagingService() {
    private val channelId = "MyNotifications"
    private val notifyId = 999
    private val smallIcon = R.mipmap.ic_launcher_round

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(
            message.notification?.title, message.notification?.body,
            message.notification?.imageUrl
        )
    }

    private fun showNotification(title: String?, message: String?, imageUrl: Uri?) {

        var channelId = "all_notification"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager, channelId)


        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("title", title)
            putExtra("message", message)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        var myBitmap: Bitmap? = null

        if (imageUrl != null) {
            val url = URL(imageUrl.toString())
            myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(smallIcon)

            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (myBitmap != null) {
            builder.setLargeIcon(myBitmap)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null)
                )
        }
        /* try {

            with(NotificationManagerCompat.from(this)) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        }catch (exp: Exception) {
            println("::::::::::::::: ERROR : $exp")
        }*/


    }


    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        channelId: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // You should create a String resource for this instead of storing in a variable
            val mChannel = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.description = "This is default channel used for all other notifications"


            notificationManager.createNotificationChannel(mChannel)
        }
    }
}

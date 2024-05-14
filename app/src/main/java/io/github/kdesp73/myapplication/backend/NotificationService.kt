package io.github.kdesp73.myapplication.backend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import io.github.kdesp73.myapplication.MainActivity
import io.github.kdesp73.myapplication.R
import java.io.FileNotFoundException
import kotlin.random.Random


class NotificationService(private val context: Context) {
    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(id: String, name: String, importance: Int){
        val notificationManager = MainActivity.appContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(NotificationChannel(
            id,
            name,
            importance
        ))
    }

    fun showBasicNotification(
        channel: String,
        title: String,
        content: String,
        @DrawableRes smallIcon: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showExpandableImageNotification(
        channel: String,
        title: String,
        content: String,
        @DrawableRes imageId: Int,
        @DrawableRes smallIcon: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(context.bitmapFromResource(imageId))
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showExpandableImageNotification(
        channel: String,
        title: String,
        content: String,
        imageUri: Uri?,
        @DrawableRes smallIcon: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(imageUri?.let { getBitmapFromUri(it) })
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

//            rotateBitmap(bitmap, 90f)
            bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source,
            0,
            0,
            source.getWidth(),
            source.getHeight(),
            matrix,
            true
        )
    }

    fun showExpandableTextNotification(
        channel: String,
        title: String,
        content: String,
        expandedText: String,
        @DrawableRes smallIcon: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(expandedText)
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showActionNotification(
        channel: String,
        title: String,
        content: String,
        actionText: String,
        actionIntent: PendingIntent,
        @DrawableRes smallIcon: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true)
            .addAction(0, actionText, actionIntent)
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun Context.bitmapFromResource(@DrawableRes resId: Int) =
        BitmapFactory.decodeResource(resources, resId)
}


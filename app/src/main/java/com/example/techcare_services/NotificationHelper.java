package com.example.techcare_services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
        public void showRepairStatusNotification(
                Context context,
                String title,
                String message) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }

            Intent intent = new Intent(context, NotificationsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, "repair_status_channel")
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent);

            NotificationManagerCompat.from(context)
                    .notify((int) System.currentTimeMillis(), builder.build());
        }

}

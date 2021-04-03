package com.ksmt.Kismaat;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class KismaatNotification extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());
    }




    public void showNotification(String title,String message)
    {

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"KismaatNotification")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.notific)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat manager =NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());
    }
}

package com.ogaga.flash.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.ogaga.flash.R;
import com.ogaga.flash.acitivies.CatalogiesActivity;
import com.ogaga.flash.models.Order;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by AKiniyalocts on 1/15/15.
 * <p/>
 * This class is just created to help with notifications, definitely not necessary.
 */
public class NotificationHelper{
    public final static String TAG = NotificationHelper.class.getSimpleName();
    private WeakReference<Context> mContext;


    public NotificationHelper(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void createUploadingNotification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_upload);
        mBuilder.setContentTitle(mContext.get().getString(R.string.push_ordered));


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.primary));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());

    }

    public void createUploadedNotification(Order order) {
        Bitmap myBitmap=null;
        InputStream in;
        try {
            URL url = new URL(order.getProduct().getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (myBitmap==null)
            return;
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        float scaleWidth = ((float) 128) / width;
        float scaleHeight = ((float) 128) / height;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                myBitmap, 0, 0, width, height, matrix, false);
        myBitmap.recycle();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());

        mBuilder.setLargeIcon(resizedBitmap);
        String titles=order.getUserBuy().getFullname()+mContext.get().getString(R.string.order_titles_push);
        mBuilder.setContentTitle(titles);
        mBuilder.setSmallIcon(R.drawable.un);
        String content=mContext.get().getString(R.string.order_count)+String.valueOf(order.getCount());
        mBuilder.setContentText(order.getProduct().getName());
        mBuilder.setColor(mContext.get().getResources().getColor(R.color.primary));
    /*   Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.data.link));
        PendingIntent intent = PendingIntent.getActivity(mContext.get(), 0, resultIntent, 0);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);*/

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shareIntent.setType("text/plain");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext.get().getApplicationContext());
        stackBuilder.addParentStack(CatalogiesActivity.class);
        stackBuilder.addNextIntent(shareIntent);

       PendingIntent pIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.addAction(new NotificationCompat.Action(R.drawable.ic_action_cart,
                mContext.get().getString(R.string.order_gostore_push), pIntent));
        mBuilder.setPriority(2);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }

 /*   public void createFailedUploadNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notification_fail));


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.primary));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }*/
}

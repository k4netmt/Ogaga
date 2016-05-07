package com.ogaga.flash.clients;

import android.content.Context;

import com.ogaga.flash.helpers.NotificationHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.ImgurAPI;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.utils.NetworkUtils;
import retrofit.RestAdapter;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.RetrofitError;
import java.lang.ref.WeakReference;

/**
 * Created by Kanet on 4/13/2016.
 */
public class ImgurClient {
    public interface ImgurClientListener{
        public void postUploadImage(ImageResponse imageResponse);
    }
    private ImgurClientListener listener;
    public static final boolean LOGGING = false;
    private static String CLIENTS_ID="6ff577b2e93264f";
    private static String CLIENTS_SECRET="9896e4f455452bda120cd037636025028e3e9649";
    public final static String TAG = ImgurClient.class.getSimpleName();
    public ImageResponse mImageResponse;
    private WeakReference<Context> mContext;

    public ImgurClient(Context context,ImgurClientListener listener) {
        this.mContext = new WeakReference<>(context);
        this.listener=listener;
    }

    public static String getClientAuth() {
        return "Client-ID " + CLIENTS_ID;
    }

    public ImageResponse Execute(Upload upload, Callback<ImageResponse> callback) {
        final Callback<ImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            //Callback will be called, so we prevent a unnecessary notification
            cb.failure(null);
            listener.postUploadImage(null);
            return null;
        }

        //notificationHelper.createUploadingNotification();

        RestAdapter restAdapter = buildRestAdapter();
        String url;

        restAdapter.create(ImgurAPI.class).postImage(
                getClientAuth(),
                upload.title,
                upload.description,
                upload.albumId,
                null,
                new TypedFile("image/*", upload.image),
                new Callback<ImageResponse>() {
                    @Override
                    public void success(ImageResponse imageResponse, Response response) {
                        if (cb != null) cb.success(imageResponse, response);
                        if (response == null) {
                            /*
                             Notify image was NOT uploaded successfully
                            */
                            //notificationHelper.createFailedUploadNotification();
                            return;
                        }
                        /*
                        Notify image was uploaded successfully
                        */
                        if (imageResponse.success) {
                            mImageResponse=imageResponse;
                            listener.postUploadImage(imageResponse);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null) cb.failure(error);
                        listener.postUploadImage(null);
                    }
                });
        return mImageResponse;
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }
}

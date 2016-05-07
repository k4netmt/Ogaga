package com.ogaga.flash.models;

import com.ogaga.flash.imgurmodel.ImageResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Kanet on 4/16/2016.
 */
public class UiCallback implements Callback<ImageResponse> {

    @Override
    public void success(ImageResponse imageResponse, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {
        //Assume we have no connection, since error is null
        if (error == null) {
            //Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }
}

package com.ogaga.flash.utils;

import android.util.Log;

import com.ogaga.flash.clients.ImgurClient;

/**
 * Created by AKiniyalocts on 1/16/15.
 *
 * Basic logger bound to a flag in Constants.java
 */
public class aLog {
  public static void w (String TAG, String msg){
    if(ImgurClient.LOGGING) {
      if (TAG != null && msg != null)
        Log.w(TAG, msg);
    }
  }

}

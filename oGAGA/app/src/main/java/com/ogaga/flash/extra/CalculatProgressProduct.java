package com.ogaga.flash.extra;

import android.content.Context;

import com.ogaga.flash.R;
import com.ogaga.flash.models.Product;

/**
 * Created by Kanet on 5/2/2016.
 */
public class CalculatProgressProduct {

    public static String disDate;
    public static String nextStatus;
    public static void CalculatProgress(Context context,Product product){
        String[] arrStatus=context.getResources().getStringArray(R.array.product_status_edit);
        if (product.getStart_date()>System.currentTimeMillis()  ){
            disDate=TimeStamp.getDistanceTimeFromCurrent(product.getStart_date(), TimeStamp.FULL_TIME);
            nextStatus=context.getResources().getString(R.string.next_status)+" " + arrStatus[0];
            return;
        }
        if (product.getStart_date()<=System.currentTimeMillis() && System.currentTimeMillis()<product.getFinished_date()){
            disDate=TimeStamp.getDistanceTimeFromCurrent(product.getFinished_date(),TimeStamp.FULL_TIME);
            nextStatus=context.getResources().getString(R.string.next_status)+" " + arrStatus[1];
            return;
        }
        if (product.getFinished_date()<=System.currentTimeMillis()){
            disDate=context.getResources().getString(R.string.status_sold);
            nextStatus="";
            return;
        }

    }
}

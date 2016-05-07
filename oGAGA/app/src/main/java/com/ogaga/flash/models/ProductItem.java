package com.ogaga.flash.models;

import java.io.Serializable;

/**
 * Created by IceStone on 4/24/2016.
 */
public class ProductItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mProductName;
    private String mProducer;
    private String mOrigin;
    private String mPrice;
    private String Status;
    private String mImage;

    public ProductItem(String productName, String producer, String origin, String price, String status, String image) {
        mProductName = productName;
        mProducer = producer;
        mOrigin = origin;
        mPrice = price;
        Status = status;
        mImage = image;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getProducer() {
        return mProducer;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getStatus() {
        return Status;
    }

//    public String getImage() {
//        return mImage;
//    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "mProductName='" + mProductName + '\'' +
                ", mProducer='" + mProducer + '\'' +
                ", mOrigin='" + mOrigin + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", Status='" + Status + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}

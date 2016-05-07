package com.ogaga.flash.models;

import org.parceler.Parcel;

/**
 * Created by Kanet on 4/25/2016.
 */
@Parcel
public class Order {
    private User userBuy;
    private Integer count;
    private long created_at;
    private long prices;
    private long id_order;
    private int id_shipping_status;
    private boolean bFlag_push;
    private String address_Shipping;
    private String phonenumber_Shipping;
    private String description;
    private Product product;

    public boolean isbFlag_push() {
        return bFlag_push;
    }

    public void setbFlag_push(boolean bFlag_push) {
        this.bFlag_push = bFlag_push;
    }

    public String getAddress_Shipping() {
        return address_Shipping;
    }

    public void setAddress_Shipping(String address_Shipping) {
        this.address_Shipping = address_Shipping;
    }

    public String getPhonenumber_Shipping() {
        return phonenumber_Shipping;
    }

    public void setPhonenumber_Shipping(String phonenumber_Shipping) {
        this.phonenumber_Shipping = phonenumber_Shipping;
    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public int getId_shipping_status() {
        return id_shipping_status;
    }

    public void setId_shipping_status(int id_shipping_status) {
        this.id_shipping_status = id_shipping_status;
    }

    public User getUserBuy() {
        return userBuy;
    }

    public void setUserBuy(User userBuy) {
        this.userBuy = userBuy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getPrices() {
        return prices;
    }

    public void setPrices(long prices) {
        this.prices = prices;
    }


    public Order(User userBuy, Integer count, long created_at, long prices, long id_order, int id_shipping_status, boolean bFlag_push, String address_Shipping, String phonenumber_Shipping, String description, Product product) {
        this.userBuy = userBuy;
        this.count = count;
        this.created_at = created_at;
        this.prices = prices;
        this.id_order = id_order;
        this.id_shipping_status = id_shipping_status;
        this.bFlag_push = bFlag_push;
        this.address_Shipping = address_Shipping;
        this.phonenumber_Shipping = phonenumber_Shipping;
        this.description = description;
        this.product = product;
    }

    public Order() {
    }
}

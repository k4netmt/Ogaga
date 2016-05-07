package com.ogaga.flash.models;

import org.parceler.Parcel;

/**
 * Created by Kanet on 5/5/2016.
 */
@Parcel
public class Like {
    private User userLike;
    private long created_at;
    private long id_like;
    private boolean bFlag_push;
    private Product product;

    public User getUserLike() {
        return userLike;
    }

    public void setUserLike(User userLike) {
        this.userLike = userLike;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getId_like() {
        return id_like;
    }

    public void setId_like(long id_like) {
        this.id_like = id_like;
    }

    public boolean isbFlag_push() {
        return bFlag_push;
    }

    public void setbFlag_push(boolean bFlag_push) {
        this.bFlag_push = bFlag_push;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Like(User userLike, long created_at, long id_like, boolean bFlag_push, Product product) {
        this.userLike = userLike;
        this.created_at = created_at;
        this.id_like = id_like;
        this.bFlag_push = bFlag_push;
        this.product = product;
    }

    public Like() {
    }
}

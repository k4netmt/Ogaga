package com.ogaga.flash.models;

import android.graphics.Bitmap;

import org.parceler.Parcel;

/**
 * Created by Kanet on 4/13/2016.
 */
@Parcel
public class Catalogies {

    private long id;
    private String name;
    private long created_at;
    private String description;
    private String url;
    private int localImage;
    public Catalogies(long id, String name, long created_at, String description, String url) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.description = description;
        this.url = url;
    }

    public Catalogies() {
    }

    public Catalogies(String name, int localImage) {
        setLocalImage(localImage);
        setName(name);
    }

    public int getLocalImage() {
        return localImage;
    }

    public void setLocalImage(int localImage) {
        this.localImage = localImage;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated_at() {
        return created_at;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}

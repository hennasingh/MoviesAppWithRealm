package com.artist.web.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 06-Mar-18.
 */

public class MovieVideos {

    @SerializedName("id")
    private String mId;

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @SerializedName("type")
    private String mType;

    public MovieVideos(String id, String key, String name, String type) {
        mId = id;
        mKey = key;
        mName = name;
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}

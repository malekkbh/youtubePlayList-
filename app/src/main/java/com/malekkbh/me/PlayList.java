package com.malekkbh.me;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;

public class PlayList implements Parcelable {
    private String title;
    private String link;
    private String img ;


    public PlayList(String title, String link, String img) {
        this.title = title;
        this.link = link;
        this.img = img;
    }

    protected PlayList(Parcel in) {
        title = in.readString();
        link = in.readString();
        img = in.readString();
    }

    public static final Creator<PlayList> CREATOR = new Creator<PlayList>() {
        @Override
        public PlayList createFromParcel(Parcel in) {
            return new PlayList(in);
        }

        @Override
        public PlayList[] newArray(int size) {
            return new PlayList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(img);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
package com.ujjawalayush.example.kamsh.Data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class AddpostData implements Parcelable {
    private String id,content,title;
    private String uri;
    private long height;

    protected AddpostData(Parcel in) {
        id = in.readString();
        content = in.readString();
        title = in.readString();
    }

    public static final Creator<AddpostData> CREATOR = new Creator<AddpostData>() {
        @Override
        public AddpostData createFromParcel(Parcel in) {
            return new AddpostData(in);
        }

        @Override
        public AddpostData[] newArray(int size) {
            return new AddpostData[size];
        }
    };

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public AddpostData() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(title);
    }

    public void setUri(Uri uri) {
        this.uri=uri.toString();
    }

    public String getUri() {
        return uri;
    }
}

package com.example.a39_project_videoplayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDescription {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_id")
    @Expose
    private Object categoryId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("file_mp4")
    @Expose
    private String fileMp4;
    @SerializedName("date_published")
    @Expose
    private String datePublished;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFileMp4() {
        return fileMp4;
    }

    public void setFileMp4(String fileMp4) {
        this.fileMp4 = fileMp4;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

}
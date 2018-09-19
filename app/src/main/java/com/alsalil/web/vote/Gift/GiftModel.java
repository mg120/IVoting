package com.alsalil.web.vote.Gift;

/**
 * Created by Ma7MouD on 2/20/2018.
 */

public class GiftModel {

    private String imageView_url ;
    private String title ;
    private String description ;
    private String points_num ;

    public GiftModel(String imageView_url, String title, String description, String points_num) {
        this.imageView_url = imageView_url;
        this.title = title;
        this.description = description;
        this.points_num = points_num;
    }


    public String getImageView_url() {
        return imageView_url;
    }

    public void setImageView_url(String imageView_url) {
        this.imageView_url = imageView_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoints_num() {
        return points_num;
    }

    public void setPoints_num(String points_num) {
        this.points_num = points_num;
    }
}

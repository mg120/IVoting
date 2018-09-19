package com.alsalil.web.vote;

/**
 * Created by Ma7MouD on 2/15/2018.
 */

public class MainModel {

    private String image ;
    private String title ;
    private String cat_Id ;

    public MainModel(String image, String title, String cat_Id) {
        this.image = image;
        this.title = title;
        this.cat_Id = cat_Id;
    }

    public String getImage_id() {
        return image;
    }

    public void setImage_id(String image_id) {
        this.image = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCat_Id() {
        return cat_Id;
    }

    public void setCat_Id(String cat_Id) {
        this.cat_Id = cat_Id;
    }
}

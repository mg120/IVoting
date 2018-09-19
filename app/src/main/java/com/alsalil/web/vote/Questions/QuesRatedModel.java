package com.alsalil.web.vote.Questions;

/**
 * Created by Ma7MouD on 3/1/2018.
 */

public class QuesRatedModel {

    private String item_title ;
    private String item_image ;

    public QuesRatedModel(String item_title, String item_image) {
        this.item_title = item_title;
        this.item_image = item_image;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }
}

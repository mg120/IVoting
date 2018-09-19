package com.alsalil.web.vote.Section;

/**
 * Created by Ma7MouD on 2/20/2018.
 */

public class SectionModel {

    private String section_item_img;
    private String section_item_title;
    private String item_id ;

    public SectionModel(String section_item_img, String section_item_title, String item_id) {
        this.section_item_img = section_item_img;
        this.section_item_title = section_item_title;
        this.item_id = item_id;
    }

    public String getSection_item_img() {
        return section_item_img;
    }

    public void setSection_item_img(String section_item_img) {
        this.section_item_img = section_item_img;
    }

    public String getSection_item_title() {
        return section_item_title;
    }

    public void setSection_item_title(String section_item_title) {
        this.section_item_title = section_item_title;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}

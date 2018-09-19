package com.alsalil.web.vote.AllRates;

/**
 * Created by Ma7MouD on 3/12/2018.
 */

public class AllRatesModel {

    private String itemId ;
    private String itemName ;
    private String imageName ;


    public AllRatesModel(String itemId, String itemName, String imageName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageName = imageName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}

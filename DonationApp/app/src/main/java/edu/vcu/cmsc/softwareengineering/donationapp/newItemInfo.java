package edu.vcu.cmsc.softwareengineering.donationapp;

public class newItemInfo {
    public String itemDescription;
    public String itemCategory;
    public String itemCondition;
    public String itemDeliveryMethod;
    public String itemQuantity;

    public newItemInfo (String description, String category, String condition,
                        String deliveryMethod, String quantity){
        this.itemDescription = description;
        this.itemCategory = category;
        this.itemCondition = condition;
        this.itemDeliveryMethod = deliveryMethod;
        this.itemQuantity = quantity;
    }
}


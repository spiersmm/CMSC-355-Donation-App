// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;


import com.google.firebase.database.Exclude;

public class newItemInfo {
    public String itemDescription;
    public String itemCategory;
    public String itemCondition;
    public String itemDeliveryMethod;
    public String itemQuantity;
    public String itemImageUrl;
    public String mKey;

    public newItemInfo() { }


    public newItemInfo (String description, String category, String condition,
                        String deliveryMethod, String quantity, String imageUrl){
        this.itemDescription = description;
        this.itemCategory = category;
        this.itemCondition = condition;
        this.itemDeliveryMethod = deliveryMethod;
        this.itemQuantity = quantity;
        this.itemImageUrl = imageUrl;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public String getItemDeliveryMethod() {
        return itemDeliveryMethod;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemQuantity() {
        return  itemQuantity;
    }

    public String getItemImageUrl() {return itemImageUrl; }

    @Exclude
    public String getKey() {return mKey; }


    public void setItemCategory(String category) { this.itemCategory = category; }

    public void setItemCondition(String condition) { this.itemCondition = condition; }

    public void setItemDeliveryMethod(String deliveryMethod) { this.itemDeliveryMethod = deliveryMethod; }

    public void setItemDescription(String description) { this.itemDescription = description; }

    public void setItemQuantity(String quantity) { this.itemQuantity = quantity; }

    public void setItemImageUrl(String imageUrl) {this.itemImageUrl = imageUrl; }

    @Exclude
    public void setKey(String key) {mKey = key; }




    @Override
    public String toString() {
        return "Description: " + itemDescription +
                ", Category: " + itemCategory +
                ", Condition: " + itemCondition +
                ", Delivery Method: " + itemDeliveryMethod +
                ", Quantity: " + itemQuantity +
                ", Image URL: " + itemImageUrl;
    }

}


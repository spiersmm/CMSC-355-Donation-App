// Project B Team 1
// CMSC 355 Spring 2020
package edu.vcu.cmsc.softwareengineering.donationapp;


import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class newItemInfo {
    private String email;
    private String itemDescription;
    private String itemCategory;
    private String itemCondition;
    private String itemDeliveryMethod;
    private String itemQuantity;
    private String itemImageUrl;
    private String mKey;
    private String recipientName;

    public newItemInfo() { }


    public newItemInfo (String description, String category, String condition,
                        String deliveryMethod, String quantity, String imageUrl, String email){
        this.itemDescription = description;
        this.itemCategory = category;
        this.itemCondition = condition;
        this.itemDeliveryMethod = deliveryMethod;
        this.itemQuantity = quantity;
        this.itemImageUrl = imageUrl;
        this.email = email;
    }

    public newItemInfo (String description, String category, String condition,
                        String deliveryMethod, String quantity, String imageUrl, String email, String recipientName){
        this.itemDescription = description;
        this.itemCategory = category;
        this.itemCondition = condition;
        this.itemDeliveryMethod = deliveryMethod;
        this.itemQuantity = quantity;
        this.itemImageUrl = imageUrl;
        this.email = email;
        this.recipientName = recipientName;
    }

    public static List<String> getCategoriesList() {
        String[] s = {"Clothes", "Food", "Furniture", "Toiletries", "Games/Toys", "Books", "Electronics", "Other"};
        return Arrays.asList(s);
    }

    public static List<String> getConditionList() {
        String[] s = {"Perfect", "Ok", "Poor"};
        return Arrays.asList(s);
    }

    public static List<String> getDeliveryMethodList() {
        String[] s = {"Pickup", "Delivery"};
        return Arrays.asList(s);
    }

    public static List<String> getQuantityList() {
        String[] s = {"1", "2", "3", "4", "5", "Over 5"};
        return Arrays.asList(s);
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

    public String getEmail() {return email; }

    public String getRecipientName() {return recipientName; }

    @Exclude
    public String getKey() {return mKey; }


    public void setItemCategory(String category) { this.itemCategory = category; }

    public void setItemCondition(String condition) { this.itemCondition = condition; }

    public void setItemDeliveryMethod(String deliveryMethod) { this.itemDeliveryMethod = deliveryMethod; }

    public void setItemDescription(String description) { this.itemDescription = description; }

    public void setItemQuantity(String quantity) { this.itemQuantity = quantity; }

    public void setItemImageUrl(String imageUrl) { this.itemImageUrl = imageUrl; }

    public void setEmail(String email) { this.email = email; }

    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }


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


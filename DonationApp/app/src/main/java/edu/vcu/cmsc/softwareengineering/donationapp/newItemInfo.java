package edu.vcu.cmsc.softwareengineering.donationapp;



public class newItemInfo {
    private String itemDescription;
    private String itemCategory;
    private String itemCondition;
    private String itemDeliveryMethod;
    private String itemQuantity;

    public newItemInfo() {}


    public newItemInfo (String description, String category, String condition,
                        String deliveryMethod, String quantity){
        this.itemDescription = description;
        this.itemCategory = category;
        this.itemCondition = condition;
        this.itemDeliveryMethod = deliveryMethod;
        this.itemQuantity = quantity;
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

    @Override
    public String toString() {
        return "Description: " + itemDescription + ", Category: " + itemCategory + ", Condition: " + itemCondition + ", Delivery Method: " + itemDeliveryMethod + ", Quantity: " + itemQuantity;
    }

}


// Project B Team 1
// CMSC 355 Spring 2020
// Junit Test for newItemInfo class

package edu.vcu.cmsc.softwareengineering.donationapp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class newItemInfoUnitTest {
    private newItemInfo newItem;
    private newItemInfo newItem2;

    @Before
    public void setUp() throws Exception {
        newItem = new newItemInfo("TV", "Electronics", "Perfect", "Pickup", "1", "url", "email");
        newItem2 = new newItemInfo("TV", "Electronics", "Perfect", "Pickup", "1", "url", "email", "goodwill");
    }
    @Test
    public void testGetItemDescription() {
        assertTrue(newItem.getItemDescription().equals("TV"));
    }
    @Test
    public void testGetItemCategory() {
        assertTrue(newItem.getItemCategory().equals("Electronics"));
    }
    @Test
    public void testGetItemCondition() {
        assertTrue(newItem.getItemCondition().equals("Perfect"));
    }
    @Test
    public void testGetItemDeliveryMethod() {
        assertTrue(newItem.getItemDeliveryMethod().equals("Pickup"));
    }
    @Test
    public void testGetItemQuantity() {
        assertTrue(newItem.getItemQuantity().equals("1"));
    }
    @Test
    public void testGetItemImageUrl() {
        assertTrue(newItem.getItemImageUrl().equals("url"));
    }
    @Test
    public void testGetEmail() {
        assertTrue(newItem.getEmail().equals("email"));
    }
    @Test
    public void testGetRecipientName() {
        assertTrue(newItem2.getRecipientName().equals("goodwill"));
    }
    @Test
    public void testSetItemDescription() {
        newItem.setItemDescription("Bed");
        assertTrue(newItem.getItemDescription().equals("Bed"));
    }
    @Test
    public void testSetItemCategory() {
        newItem.setItemCategory("Furniture");
        assertTrue(newItem.getItemCategory().equals("Furniture"));
    }
    @Test
    public void testSetItemCondition() {
        newItem.setItemCondition("Ok");
        assertTrue(newItem.getItemCondition().equals("Ok"));
    }
    @Test
    public void testSetItemDeliveryMethod() {
        newItem.setItemDeliveryMethod("Delivery");
        assertTrue(newItem.getItemDeliveryMethod().equals("Delivery"));
    }
    @Test
    public void testSetItemQuantity() {
        newItem.setItemQuantity("2");
        assertTrue(newItem.getItemQuantity().equals("2"));
    }
    @Test
    public void testSetItemImageUrl() {
        newItem.setItemImageUrl("bed.jpeg");
        assertTrue(newItem.getItemImageUrl().equals("bed.jpeg"));
    }
    @Test
    public void testSetEmail() {
        newItem.setEmail("test@gmail.com");
        assertTrue(newItem.getEmail().equals("test@gmail.com"));
    }
    @Test
    public void testSetRecipientName() {
        newItem2.setRecipientName("vcu");
        assertTrue(newItem2.getRecipientName().equals("vcu"));
    }
}

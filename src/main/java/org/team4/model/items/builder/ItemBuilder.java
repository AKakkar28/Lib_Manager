package org.team4.model.items.builder;

import org.team4.model.items.Item;

//Abstract builder with common parameters
public abstract class ItemBuilder<T extends Item> {
 protected String title;
 protected int yearPublished;
 protected int quantity;
 protected double price;
 
 public ItemBuilder<T> title(String title) {
     this.title = title;
     return this;
 }

 public ItemBuilder<T> yearPublished(int yearPublished) {
     this.yearPublished = yearPublished;
     return this;
 }

 public ItemBuilder<T> quantity(int quantity) {
     this.quantity = quantity;
     return this;
 }
 
 public ItemBuilder<T> yearPublished(double price) {
     this.price = price;
     return this;
 }
 
 // Abstract build method to be implemented by concrete builders
 public abstract T build();
}
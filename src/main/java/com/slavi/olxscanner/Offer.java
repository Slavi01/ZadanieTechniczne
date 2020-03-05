package com.slavi.olxscanner;

public class Offer {
  private String id;
  private String title;
  private String price;

  public Offer() {
  }

  public Offer(String id, String title, String price) {
    this.id = id;
    this.title = title;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public Offer setId(String id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Offer setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getPrice() {
    return price;
  }

  public Offer setPrice(String price) {
    this.price = price;
    return this;
  }
}

package com.slavi.olxscanner;

import java.util.ArrayList;
import java.util.List;

public class OfferListResponse {
  private List<Offer> offers;

  public OfferListResponse() {
    offers = new ArrayList<Offer>();
  }

  public void addOffer(Offer offer) {
    this.offers.add(offer);
  }

  public List<Offer> getOffers() {
    return offers;
  }
}

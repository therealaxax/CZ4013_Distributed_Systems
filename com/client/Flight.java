package com.client;

import java.io.Serializable;

public class Flight implements Serializable {
  Integer flightIdentifier; // 4 bytes
  String source; // variable bytes
  String destination; // variable bytes
  String time; // dont know
  float price; // 4 bytes
  Integer seatsAvailable; // 4 bytes
  
  Flight() {
    this.flightIdentifier = 10;
    this.source = "bob";
    this.destination = "italy";
    this.time = "24-02-2023 06:29:33";
    this.price = 5.50f;
    this.seatsAvailable = 100;
  }

  Flight(Integer flightIdentifier, String source, String destination, String time, float price, Integer seatsAvailable){
    this.flightIdentifier = flightIdentifier;
    this.source = source;
    this.destination = destination;
    this.time = time;
    this.price = price;
    this.seatsAvailable = seatsAvailable;
  }

  public Integer getFlightIdentifier() {
    return flightIdentifier;
  }

  public String getSource() {
    return source;
  }

  public String getDestination() {
    return destination;
  }

  public String getTime() {
    return time;
  }

  public float getPrice() {
    return price;
  }

  public Integer getSeatsAvailable() {
    return seatsAvailable;
  }

}

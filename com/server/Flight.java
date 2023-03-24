package com.server;

import java.io.Serializable;

public class Flight implements Serializable {
  Integer flightIdentifier; // 4 bytes
  String source; // variable bytes
  String destination; // variable bytes
  String time; // dont know
  float price; // 4 bytes
  Integer seatsAvailable; // 4 bytes
  
  public Flight() {
    this.flightIdentifier = 10;
    this.source = "bob";
    this.destination = "italy";
    this.time = "24-02-2023 06:29:33";
    this.price = 5.50f;
    this.seatsAvailable = 100;
  }

  public Flight(Integer flightIdentifier, String source, String destination, String time, float price, Integer seatsAvailable){
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

  public void setTime(String time) {
    this.time = time;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public void setSeatsAvailable(Integer seatsAvailable) {
    this.seatsAvailable = seatsAvailable;
  }

  public void setFlightIdentifier(Integer flightIdentifier) {
    this.flightIdentifier = flightIdentifier;
  }

}

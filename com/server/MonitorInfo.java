package com.server;

import java.util.*;

public class MonitorInfo {
  Integer flightIdentifier;
  Integer monitorInterval;
  Date firstListened;
  
  public MonitorInfo(Integer flightIdentifier, Integer monitorInterval, Date firstListened){
    this.flightIdentifier = flightIdentifier;
    this.monitorInterval = monitorInterval;
    this.firstListened = firstListened;
  }

  public Date getFirstListened() {
    return firstListened;
  }

  public void setFirstListened(Date firstListened) {
    this.firstListened = firstListened;
  }

  public Integer getMonitorInterval() {
    return monitorInterval;
  }

  public void setMonitorInterval(Integer monitorInterval) {
    this.monitorInterval = monitorInterval;
  }

  public Integer getFlightIdentifier() {
    return flightIdentifier;
  }

  public void setFlightIdentifier(Integer flightIdentifier) {
    this.flightIdentifier = flightIdentifier;
  }
}

package com.server;

import java.net.*;
import java.util.*;

public class Functions {

  private ArrayList<AttributeValue> avList;
  private ArrayList<Flight> flights;
  DatagramSocket serverSocket;
  DatagramPacket inputPacket;

  public Functions(ArrayList<AttributeValue> avList, ArrayList<Flight> flights, DatagramSocket serverSocket, DatagramPacket inputPacket){
    this.avList = avList;
    this.flights = flights;
    this.serverSocket = serverSocket;
    this.inputPacket = inputPacket;
  }

  // Case 1
  public void checkFlightID(){
    String source = ((AttributeValueString) avList.get(2)).getValue();
    String destination = ((AttributeValueString) avList.get(3)).getValue();
    ArrayList<AttributeValue> returnList = new ArrayList<>();
    returnList.add(new AttributeValueString("Success Message", "Successfully retrieved Flight Identifier/s!!"));
    
    // Iterate database to find flight which matches source and destination
    for(Flight flight : flights){
      if(flight.getSource().equals(source) && flight.getDestination().equals(destination)){
        returnList.add(new AttributeValueInt("Flight Identifier", flight.getFlightIdentifier()));
      }
    }
    if(returnList.size() == 0){
      returnList.clear();
      returnList.add(new AttributeValueString("Error Message", "No flights with source " + source + " and destination " + destination + " found!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }

  // Case 2
  public void checkTimePriceSeatsWithFlightID(){
    Integer flightIdentifier = ((AttributeValueInt) avList.get(2)).getValue();

    ArrayList<AttributeValue> returnList = new ArrayList<>();

    // Iterate database to find flight which matches flightIdentifier
    for(Flight flight : flights){
      if(flight.getFlightIdentifier().equals(flightIdentifier)){
        returnList.add(new AttributeValueString("Success Message", "Successfully retrieved information!!"));
        returnList.add(new AttributeValueString("Departure Time", flight.getTime()));
        returnList.add(new AttributeValueFloat("Airfare", flight.getPrice()));
        returnList.add(new AttributeValueInt("Seat Availability", flight.getSeatsAvailable()));
      }
    }
    if(returnList.isEmpty()){
      returnList.add(new AttributeValueString("Error Message", "No flights with Flight ID " + flightIdentifier + " found!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }

  // Case 3
  public void bookSeatsWithFlightID(HashMap<SocketAddress, MonitorInfo> listeners){
    Integer flightIdentifier = ((AttributeValueInt) avList.get(2)).getValue();
    Integer numberOfSeats = ((AttributeValueInt) avList.get(3)).getValue();
    ArrayList<AttributeValue> returnList = new ArrayList<>();

    // Iterate database to find flight which matches flightIdentifier
    for(Flight flight : flights){
      if(flight.getFlightIdentifier().equals(flightIdentifier)){

        // Check if there are enough seats to book
        if(flight.getSeatsAvailable() >= numberOfSeats){
          flight.setSeatsAvailable(flight.getSeatsAvailable() - numberOfSeats);
          returnList.add(new AttributeValueString("Success Message", numberOfSeats + " Seats successfully booked!!"));
          returnList.add(new AttributeValueInt("No.of available seats left for Flight ID " + flight.getFlightIdentifier(), flight.getSeatsAvailable()));

          // Callback for seat availability
          for(Map.Entry<SocketAddress, MonitorInfo> listener : listeners.entrySet()){
            SocketAddress clientSocketAddress = listener.getKey();
            Integer clientFlightIdentifier = listener.getValue().getFlightIdentifier();
            Integer monitorInterval = listener.getValue().getMonitorInterval();
            Date firstListened  = listener.getValue().getFirstListened();
            long currentTime = new Date().getTime();

            ArrayList<AttributeValue> monitorList = new ArrayList<>();
            if(clientFlightIdentifier.equals(flightIdentifier)){

              // Check if monitor interval is still valid
              if(currentTime - firstListened.getTime() < monitorInterval * 1000){

                // Send callback message to client
                monitorList.add(new AttributeValueString("Monitor Seats Availability Callback", "Seat Availability of Flight ID " + flightIdentifier + " changed to " + flight.getSeatsAvailable()));
                Utils.sendPacket(serverSocket, monitorList, clientSocketAddress);
              }
              else{

                // Remove client address from monitoring map
                listeners.remove(listener.getKey());
              }
            }
          }
        }
        else{
          returnList.add(new AttributeValueString("Error Message", "There is/are only " + flight.getSeatsAvailable() + " seat/s left. Please try again!!!"));
          break;
        }
      }
    }
    if(returnList.isEmpty()){
      returnList.add(new AttributeValueString("Error message", "No flights with Flight ID " + flightIdentifier + " found!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }

  // Case 4
  public void monitorSeatAvailability(HashMap<SocketAddress, MonitorInfo> listeners){
    Integer flightIdentifier = ((AttributeValueInt) avList.get(2)).getValue();
    Integer monitorInterval = ((AttributeValueInt) avList.get(3)).getValue();
    ArrayList<AttributeValue> returnList = new ArrayList<>();

    // Map client socket address to monitoring details
    listeners.put(inputPacket.getSocketAddress(), new MonitorInfo(flightIdentifier, monitorInterval, new Date()));

    // Iterate database to find flight which matches flightIdentifier
    for(Flight flight : flights){
      if(flight.getFlightIdentifier().equals(flightIdentifier)){
        returnList.add(new AttributeValueString("Success Message", "Monitoring seats of Flight ID " + flightIdentifier));
      }
    }
    if(returnList.isEmpty()){
      returnList.add(new AttributeValueString("Error Message", "No flights with Flight ID " + flightIdentifier + " found!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }

  // Case 5
  public void checkAllDestinationsWithSource(){
    String source = ((AttributeValueString) avList.get(2)).getValue();
    ArrayList<AttributeValue> returnList = new ArrayList<>();
    returnList.add(new AttributeValueString("Success Message", "Retrieved Destinations successfully!!"));

    // Iterate database to find flight which matches source
    for(Flight flight : flights){
      if(flight.getSource().equals(source)){
        returnList.add(new AttributeValueFloat("Airfare to " + flight.getDestination() + " on " + flight.getTime() + " in SGD" , flight.getPrice()));
      }
    } 
    if(returnList.size() == 1){
      returnList.clear();
      returnList.add(new AttributeValueString("Error Message", "No flights with source " + source + " found!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }

  // Case 6
  public void increaseOrDecreaseAirfareWithFlightID(){
    Integer flightIdentifier = ((AttributeValueInt) avList.get(2)).getValue();
    Integer priceChange = ((AttributeValueInt) avList.get(3)).getValue();
    ArrayList<AttributeValue> returnList = new ArrayList<>();

    // Iterate database to find flight which matches flightIdentifier
    for(Flight flight : flights){
      if(flight.getFlightIdentifier().equals(flightIdentifier)){
        // Check if price change is valid
        if(flight.getPrice() + priceChange >= 0){
           flight.setPrice(flight.getPrice() + priceChange);
           returnList.add(new AttributeValueString("Success Message", "Airface changed successfully!!"));
           returnList.add(new AttributeValueFloat("Airfare of FlightID " + flight.getFlightIdentifier() + " changed to" , flight.getPrice()));
        }
        else{
           returnList.add(new AttributeValueString("Error Message", "Price change will make the airfare negative!! Not Allowed!!"));
        }
      }
    }
    if(returnList.isEmpty()){
      returnList.add(new AttributeValueString("Error Message", "No flights with Flight ID " + flightIdentifier + " found!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }

  // Case 7
  public void changeInvocation(){
    Integer invocation = ((AttributeValueInt) avList.get(2)).getValue();
    ArrayList<AttributeValue> returnList = new ArrayList<>();

    if(invocation == 1){
      Main.isAtLeastOnce = true;
      returnList.add(new AttributeValueString("Success Message ", "Successfully changed to At-Least-Once invocation!!"));
    }
    else if(invocation == 2){
      Main.isAtLeastOnce = false;
      returnList.add(new AttributeValueString("Success Message ", "Successfully changed to At-Most-Once invocation!!"));
    }
    else{
      returnList.add(new AttributeValueString("Error Message", "Invocation option is not valid!!"));
    }

    // Send packet to client
    Utils.sendPacket(serverSocket, returnList, inputPacket);
  }
}

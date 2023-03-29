package com.client;

import java.net.*;
import java.util.*;

public class Functions {
  public final static long NANOSEC_PER_SEC = 1000l * 1000 * 1000;

  DatagramSocket clientSocket;
  InetSocketAddress serverAddress;
  Scanner sc;

  public Functions(DatagramSocket clientSocket, InetSocketAddress serverAddress, Scanner sc){
    this.clientSocket = clientSocket;
    this.serverAddress = serverAddress;
    this.sc = sc;
  }

  // Case 1
  public void checkFlightID(){
    AttributeValueString optionAV = new AttributeValueString("option", "1");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Enter Source: ");
    String sourceInput = sc.nextLine();
    AttributeValueString sourceAV = new AttributeValueString("source", sourceInput);

    System.out.println("Enter Destination: ");
    String destinationInput = sc.nextLine();
    AttributeValueString destinationAV = new AttributeValueString("destination", destinationInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(sourceAV);
    requestList.add(destinationAV);

    // Keep sending request to server if no reply is received
    boolean packetSentAndReceived = false;
    while(!packetSentAndReceived){
      Utils.sendPacket(clientSocket, requestList, serverAddress);
      packetSentAndReceived = Utils.receivePacket(clientSocket);
    }
  }

  // Case 2
  public void checkTimePriceSeatsWithFlightID(){
    AttributeValueString optionAV = new AttributeValueString("option", "2");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Enter Flight ID: ");
    Integer flightIdentifierInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt flightIdentifierAV = new AttributeValueInt("flightIdentifier", flightIdentifierInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(flightIdentifierAV);

    // Keep sending request to server if no reply is received
    boolean packetSentAndReceived = false;
    while(!packetSentAndReceived){
      Utils.sendPacket(clientSocket, requestList, serverAddress);
      packetSentAndReceived = Utils.receivePacket(clientSocket);
    }
  }

  // Case 3
  public void bookSeatsWithFlightID(){
    AttributeValueString optionAV = new AttributeValueString("option", "3");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Enter Flight ID: ");
    Integer flightIdentifierInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt flightIdentifierAV = new AttributeValueInt("flightIdentifier", flightIdentifierInput);

    System.out.println("Enter Number of seats to reserve: ");
    Integer numberOfSeatsInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt numberOfSeatsAV = new AttributeValueInt("numberOfSeats", numberOfSeatsInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(flightIdentifierAV);
    requestList.add(numberOfSeatsAV);

    // Keep sending request to server if no reply is received
    boolean packetSentAndReceived = false;
    while(!packetSentAndReceived){
      Utils.sendPacket(clientSocket, requestList, serverAddress);
      packetSentAndReceived = Utils.receivePacket(clientSocket);
    }
  }

  // Case 4
  public void monitorSeatAvailability(){
    AttributeValueString optionAV = new AttributeValueString("option", "4");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Enter Flight ID: ");
    Integer flightIdentifierInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt flightIdentifierAV = new AttributeValueInt("flightIdentifier", flightIdentifierInput);

    System.out.println("Enter Monitor Interval (in seconds): ");
    Integer monitorIntervalInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt monitorIntervalAV = new AttributeValueInt("monitorInterval", monitorIntervalInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(flightIdentifierAV);
    requestList.add(monitorIntervalAV);
    Utils.sendPacket(clientSocket, requestList, serverAddress);

    // Listen to replies on server according to monitor interval specified
    long startTime = System.nanoTime();
    while(System.nanoTime() - startTime < monitorIntervalInput * NANOSEC_PER_SEC){
      System.out.println("Monitoring Seat Avalability for Flight ID " + flightIdentifierInput);
      Utils.receivePacket(clientSocket, monitorIntervalInput);
    }
  }

  // Case 5
  public void checkAllDestinationsWithSource(){
    AttributeValueString optionAV = new AttributeValueString("option", "5");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Enter Source: ");
    String sourceInput = sc.nextLine();
    AttributeValueString sourceAV = new AttributeValueString("source", sourceInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(sourceAV);

    // Keep sending request to server if no reply is received
    boolean packetSentAndReceived = false;
    while(!packetSentAndReceived){
      Utils.sendPacket(clientSocket, requestList, serverAddress);
      packetSentAndReceived = Utils.receivePacket(clientSocket);
    }
  }

  // Case 6
  public void increaseOrDecreaseAirfareWithFlightID(){
    AttributeValueString optionAV = new AttributeValueString("option", "6");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Enter Flight ID: ");
    Integer flightIdentifierInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt flightIdentifierAV = new AttributeValueInt("flightIdentifier", flightIdentifierInput);

    System.out.println("Enter Price Change (can be negative/positive): ");
    Integer priceChangeInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt priceChangeAV = new AttributeValueInt("priceChange", priceChangeInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(flightIdentifierAV);
    requestList.add(priceChangeAV);

    // Keep sending request to server if no reply is received
    boolean packetSentAndReceived = false;
    while(!packetSentAndReceived){
      Utils.sendPacket(clientSocket, requestList, serverAddress);
      packetSentAndReceived = Utils.receivePacket(clientSocket);
    }
  }

  // Case 7
  public void changeInvocation(){
    AttributeValueString optionAV = new AttributeValueString("option", "7");
    AttributeValueInt requestIdCountAV = new AttributeValueInt("requestIdCount", Main.requestIdCount++);

    System.out.println("Choose Invocation Semantics (1 for At-Least-Once, 2 for At-Most-Once): ");
    Integer invocationInput = Integer.parseInt(sc.nextLine());
    AttributeValueInt invocationInputAV = new AttributeValueInt("invocation", invocationInput);

    // Add all Attribute-Value pairs into an array
    ArrayList<AttributeValue> requestList = new ArrayList<>();
    requestList.add(optionAV);
    requestList.add(requestIdCountAV);
    requestList.add(invocationInputAV);

    // Keep sending request to server if no reply is received
    boolean packetSentAndReceived = false;
    while(!packetSentAndReceived){
      Utils.sendPacket(clientSocket, requestList, serverAddress);
      packetSentAndReceived = Utils.receivePacket(clientSocket);
    }
  }
}

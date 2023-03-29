package com.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
	
	public final static int SERVICE_PORT=50001;
  public final static long NANOSEC_PER_SEC = 1000l * 1000 * 1000;
  public static boolean isAtLeastOnce = true;

  // Maintain message history
  public static HashMap<String, DatagramPacket> history = new HashMap<>();
  public static String requestIdGlobal;
	/** 
	 * 
	 * 
	 * @param args
	 * @throws IOException		If an I/O error occurs while reading or writing stream header
	 * @throws SocketException	If a socket error occurs when receiving or sending on socket connection
	 */
	public static void main(String[] args) throws IOException, SocketException{

    // Initialize Database
		ArrayList<Flight> database = new ArrayList<>();
    database.add(new Flight(1238945, "Singapore", "Malaysia", "24-04-2023 06:30:33", (float) 500.00, 400));
    database.add(new Flight(3123789, "Brazil", "Argentina", "15-05-2023 12:30:33", (float) 1000.00, 500));
    database.add(new Flight(4895290, "America", "France", "22-04-2023 08:18:33", (float) 10.00, 300));
    database.add(new Flight(1294890, "Singapore", "Korea", "12-04-2023 10:12:19", (float) 50.00, 300));
    database.add(new Flight(5980212, "China", "America", "21-04-2023 19:08:12", (float) 800.00, 400));
    database.add(new Flight(9290359, "Russia", "Australia", "18-05-2023 10:30:33", (float) 2000.00, 700));
    database.add(new Flight(5857203, "North Korea", "South Korea", "02-04-2023 16:32:39", (float) 1500.00, 500));
    database.add(new Flight(8587324, "New Zealand", "Japan", "20-09-2023 12:19:12", (float) 2500.00, 900));
    database.add(new Flight(4751893, "Singapore", "Japan", "19-08-2023 14:31:19", (float) 700.00, 400));
    database.add(new Flight(1994890, "Singapore", "Malaysia", "24-04-2023 07:30:33", (float) 900.00, 450));

    // Set localhost address
    InetAddress local = InetAddress.getByName("0.0.0.0");

    // Instantiate a new DatagramSocket to receive responses from the client
    DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT,local);
    
    // Keep a map client listeners + flight ID for case 4
    HashMap<SocketAddress, MonitorInfo> listeners = new HashMap<>();

    while(true) {
      byte[] receivingDataBuffer = new byte[1024];
      DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
      System.out.println("Waiting for a client to send a request...");
      
      serverSocket.receive(inputPacket);
      System.out.println("Packet received from client!!");
      
      // Get list of attribute values
      ArrayList<AttributeValue> avList = Marshaller.unmarshall(inputPacket.getData());
      Utils.printAV(avList);
      
      // Get option number
      String option = ((AttributeValueString) avList.get(0)).getValue();

      // Get requestIdCount
      Integer requestIdCount = ((AttributeValueInt) avList.get(1)).getValue();
      String requestId = requestIdCount + "_" + inputPacket.getSocketAddress().toString();
      requestIdGlobal = requestId;

      // Send back same packet from history if requestId is found
      if(history.containsKey(requestIdGlobal) && !isAtLeastOnce){
        System.out.println("RequestId found in history " + requestIdGlobal);
        Utils.sendPacket(serverSocket, history.get(requestId));
      }
      else{

        // Initialize Functions
        Functions functions = new Functions(avList, database, serverSocket, inputPacket);

        switch(option){
          case "1":
            // Return Flight Identifiers by processing Source and Destination
            functions.checkFlightID();
            break;

          case "2":
            // Return Departure Time, Airfare and Seat Availability by processing Flight identifier
            functions.checkTimePriceSeatsWithFlightID();
            break;

          case "3":
            // Book seats by processing Flight Identifier and number of seats to book
            functions.bookSeatsWithFlightID(listeners);
            break;

          case "4":
            // Inform clients which are monitoring the server within the monitor interval via a callback
            functions.monitorSeatAvailability(listeners);
            break;

          case "5":
            // Return all destinations by processing source
            functions.checkAllDestinationsWithSource();
            break;

          case "6":
            // Increase or decrease Airfare by specifying Flight Identifier and Amount
            functions.increaseOrDecreaseAirfareWithFlightID();
            break;

          case "7": 
            // Change to At-least-once or At-most-once invocation semantics
            functions.changeInvocation();
            break;
        }
      }
   }
	}
}

package com.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.InetSocketAddress;

/**
 * The main menu of the client application. Initializes the client socket and instantiates instances of Client and SendRecv.
 */
public class Main {

	public static int requestIdCount = 0;
	/** 
	 * @param args
	 * @throws SocketException
	 */
	public static void main(String[] args) throws SocketException {

    String MENU =
    "------------------------------------\n" +
    "Distributed Flight Booking System\n" +
    "------------------------------------\n" +
    "Select an option from [1-7]:\n" +
    "1. Check Flight Identifiers via Source and Destination\n" +
    "2. Check Departure Time, Airfare, Seat Availability via Flight Identifier\n" +
    "3. Book Seats via Flight Identifiers and Number of Seats\n" +
    "4. Monitor Seat Availability via Flight Identifier and Monitor Interval\n" +
    "5. Check All Destinations from Source\n" +
    "6. Increase/Decrease Airfare via Flight Identifier and Amount specified\n" +
    "7. Set Invocation Semantics (1 for At Least Once, 2 for At Most Once)\n" +
    "8. Exit\n" +
    "------------------------------------\n";

    // Change to the server's inet address !!
    InetSocketAddress serverAddress = new InetSocketAddress("10.91.68.178", 50001); 
    DatagramSocket clientSocket = new DatagramSocket();

    Scanner sc = new Scanner(System.in);
    Functions functions = new Functions(clientSocket, serverAddress, sc);
    boolean exitProgram = false;

    while (!exitProgram) {

     System.out.print(MENU);
     try{
       int optionSelected = Integer.parseInt(sc.nextLine());
       switch(optionSelected) {
         case 1:
           // Check Flight Identifier by specifying Source and Destination
           functions.checkFlightID();
           break;
         case 2:
           // Check Departure Time, Airfare and Seat Availability by specifying Flight identifier
           functions.checkTimePriceSeatsWithFlightID();
           break;
         case 3:
           // Book seats by specifying Flight Identifier and number of seats to book
           functions.bookSeatsWithFlightID();
           break;
         case 4:
           // Monitor Seat Availability of flight by specifying Flight Identifier
           functions.monitorSeatAvailability();
           break;
         case 5:
           // Check all Destinations by specifying source 
           functions.checkAllDestinationsWithSource(); // idempotent-function
           break;
         case 6:
           // Increase or decrease Airfare by specifying Flight Identifier and Amount
           functions.increaseOrDecreaseAirfareWithFlightID(); // non-idempotent function
           break;
         case 7:
           // Choose At-least-once or At-most-once invocation semantics
           functions.changeInvocation();
           break;
         case 8:
           exitProgram = true;
           break;
         default:
           System.out.println("Invalid choice! Please try again.");
           break;
         }
       }catch(Exception e){
        System.out.println("Incorrect input format. Please try again!!!");
       }
    }
    System.out.println("Shutting down!");
    sc.close();
  }}

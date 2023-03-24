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
    "1. Check Flight ID based on Source and Destination\n" +
    "2. Check Departure Time, Airfare, Seat Availability via Flight ID\n" +
    "3. Book Seats with Flight ID and Number of Seats\n" +
    "4. Monitor Seat Availability\n" +
    "5. Check Cheapest Flights via Source\n" +
    "6. Increase/Decrease Airfare via Flight ID\n" +
    "7. Set Invocation (1 for At Least Once, 2 for At Most Once)\n" +
    "8. Exit\n" +
    "------------------------------------\n";

    // InetSocketAddress serverAddress = new InetSocketAddress("192.168.50.212", 50001);
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
           functions.checkFlightID();
           break;
         case 2:
           functions.checkTimePriceSeatsWithFlightID();
           break;
         case 3:
           functions.bookSeatsWithFlightID();
           break;
         case 4:
           functions.monitorSeatAvailability();
           break;
         case 5:
           functions.checkCheapestDestinationsWithSource(); // idempotent-function
           break;
         case 6:
           functions.changeAirfareWithFlightID(); // non-idempotent function
           break;
         case 7:
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

package com.client;
import java.net.*;
import java.util.*;

public class Utils {
  public static void printAV(ArrayList<AttributeValue> avList){
    System.out.println("\n--------Information Received--------");
    for(AttributeValue av : avList){
      System.out.print(av.getAttribute() + " : ");
      if(av instanceof AttributeValueString){
        System.out.println(((AttributeValueString) av).getValue());
      }
      else if(av instanceof AttributeValueInt){
        System.out.println(((AttributeValueInt) av).getValue());
      }
      else if(av instanceof AttributeValueFloat){
        System.out.println(((AttributeValueFloat) av).getValue());
      }
    }
    System.out.println();
  }

  public static void sendPacket(DatagramSocket socket, ArrayList<AttributeValue> requestList, InetSocketAddress socketAddress){
    byte[] finalByteArray = FlightMarshaller.marshall(requestList);
    DatagramPacket requestPacket = new DatagramPacket(finalByteArray, finalByteArray.length, socketAddress);
    try{
      socket.send(requestPacket);
      System.out.println("Packet sent to server!!");
    } catch(Exception e){
      System.out.println("Packet failed to send to server!!");
      e.printStackTrace();
    }
  }

  public static boolean receivePacket(DatagramSocket clientSocket){
    byte[] receivingDataBuffer = new byte[1024]; 
    DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    
    try{
      clientSocket.setSoTimeout(1000);
      clientSocket.receive(inputPacket);
      System.out.println("Packet received from server!!");
      ArrayList<AttributeValue> avList = FlightMarshaller.unmarshall(inputPacket.getData());
      Utils.printAV(avList);
      return true;
    } catch(SocketTimeoutException e){
      System.out.println("Resending packet...");
      return false;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return false;
  }

  public static boolean receivePacketWithError(DatagramSocket clientSocket){
    byte[] receivingDataBuffer = new byte[1024]; 
    DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    
    try{
      clientSocket.receive(inputPacket);
      System.out.println("Packet received from server!!");
      ArrayList<AttributeValue> avList = FlightMarshaller.unmarshall(inputPacket.getData());
      Utils.printAV(avList);
      if(avList.get(0).getAttribute().contains("Error")){
        return true;
      }
    } catch(Exception e){
      e.printStackTrace();
    }
    return false;
  }

  public static void receivePacket(DatagramSocket clientSocket, Integer monitorInterval){
    byte[] receivingDataBuffer = new byte[1024]; 
    DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    
    try{
      clientSocket.setSoTimeout(monitorInterval * 1000);
      clientSocket.receive(inputPacket);
      System.out.println("Packet received from server!!");
      ArrayList<AttributeValue> avList = FlightMarshaller.unmarshall(inputPacket.getData());
      Utils.printAV(avList);
    } catch(SocketTimeoutException e){
      System.out.println("Seat Availability Monitoring Ended");
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}

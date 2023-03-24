package com.server;
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

  public static void sendPacket(DatagramSocket socket, ArrayList<AttributeValue> returnList, DatagramPacket inputPacket){
    byte[] finalByteArray = FlightMarshaller.marshall(returnList);
    // Store packet in history
    DatagramPacket replyPacket = new DatagramPacket(finalByteArray, finalByteArray.length, inputPacket.getSocketAddress());
    Main.history.put(Main.requestIdGlobal, replyPacket);
    try{
      boolean randomSend = (int)(Math.floor(Math.random() * 101)) >= 50;
      if(randomSend) socket.send(replyPacket);
      System.out.println("Packet sent to client!!");
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void sendPacket(DatagramSocket socket, ArrayList<AttributeValue> returnList, SocketAddress inputPacketAddress){
    byte[] finalByteArray = FlightMarshaller.marshall(returnList);
    DatagramPacket replyPacket = new DatagramPacket(finalByteArray, finalByteArray.length, inputPacketAddress);
    // Store packet in history
    Main.history.put(Main.requestIdGlobal, replyPacket);
    try{
      socket.send(replyPacket);
      System.out.println("Packet sent to client!!");
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void sendPacket(DatagramSocket socket, DatagramPacket inputPacket){
    DatagramPacket replyPacket = inputPacket;
    try{
      boolean randomSend = (int)(Math.floor(Math.random() * 101)) >= 50;
      if(randomSend) socket.send(replyPacket);
      System.out.println("Packet sent to client!!");
    } catch(Exception e){
      e.printStackTrace();
    }
  }
  // public static void sendPacket(DatagramSocket socket, ArrayList<AttributeValue> returnList, DatagramPacket inputPacket){
  //   byte[] finalByteArray = FlightMarshaller.marshall(returnList);
  //   DatagramPacket requestPacket = new DatagramPacket(finalByteArray, finalByteArray.length, inputPacket.getSocketAddress());
  //   try{
  //     socket.send(requestPacket);
  //     System.out.println("Packet sent to client!!");
  //   } catch(Exception e){
  //     e.printStackTrace();
  //   }
  // }
}

package com.server;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Marshaller {
  
  // Convert List of Attribute-Value pairs into bytes
  public static byte[] marshall(ArrayList<AttributeValue> arrayAV){
    byte[] finalByteArray = new byte[1024];
    int curLength = 0;

    // Convert Attribute-Value pairs into bytes and copy into finalByteArray
    for(AttributeValue av : arrayAV){
      byte[] temp;
      if(av instanceof AttributeValueString){
        temp = ByteConverter.convertToBytes((AttributeValueString) av);
        System.arraycopy(temp, 0, finalByteArray, curLength, temp.length);
        curLength += temp.length;
      }
      else if(av instanceof AttributeValueInt){
        temp = ByteConverter.convertToBytes((AttributeValueInt) av);
        System.arraycopy(temp, 0, finalByteArray, curLength, temp.length);
        curLength += temp.length;
      }
      else if(av instanceof AttributeValueFloat){
        temp = ByteConverter.convertToBytes((AttributeValueFloat) av);
        System.arraycopy(temp, 0, finalByteArray, curLength, temp.length);
        curLength += temp.length;
      }
    }

    // Attribute "FINAL" to indicate end of unmarshalling
    byte[] temp = ByteConverter.convertToBytes(new AttributeValueString("FINAL", ""));
    System.arraycopy(temp, 0, finalByteArray, curLength, temp.length);
    return finalByteArray;
  }

  // Convert bytes into a list of Attribute-Value pairs
  public static ArrayList<AttributeValue> unmarshall(byte[] bytes) {
    boolean isAttribute = true; // Flag to alternate between Attribute and Value
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    ArrayList<AttributeValue> avList = new ArrayList<>();

    String globalAttribute = "";
    while(true){
      if(isAttribute){
        int attributeLength = buffer.getInt();
        String attribute = "";

        // Read bytes specified by attributeLength to form the attribute string
        for(int i = 0; i < attributeLength; i++){
          Character temp = (char) (buffer.get() & 0xFF);
          attribute += temp;
        }

        // End the unmarshalling process if attribute is "FINAL"
        if(attribute.equals("FINAL")){
          break;
        };

        globalAttribute = attribute;
        isAttribute = false;
      }
      else{
        Character type = (char)(buffer.get() & 0xFF);
        int valueLength = buffer.getInt();

        // If the value type is String, read bytes specified by valueLength 
        if(type == 'S'){
          String value = "";
          for(int i = 0; i < valueLength; i++){
            Character temp = (char) (buffer.get() & 0xFF);
            value += temp;
          }
          avList.add(new AttributeValueString(globalAttribute, value));
        }

        // If the value type is Integer, read next 4 bytes
        else if(type == 'I'){
          Integer value = buffer.getInt();
          avList.add(new AttributeValueInt(globalAttribute, value));
        }

        // If the value type is Float, read next 4 bytes
        else if(type == 'F'){
          Float value = buffer.getFloat();
          avList.add(new AttributeValueFloat(globalAttribute, value));
        }

        // If the value type is Double, read next 8 bytes
        else if(type == 'D'){
          Double value = buffer.getDouble();
          avList.add(new AttributeValueDouble(globalAttribute, value));
        }

        // If the value type is Boolean, read next byte
        else if(type == 'B'){
          String value = "";
          value += (char) (buffer.get() & 0xFF);
          if(value == "0") avList.add(new AttributeValueBoolean(globalAttribute, false));
          else if(value == "1") avList.add(new AttributeValueBoolean(globalAttribute, true));
        }

        isAttribute = true;
      }
    }
    return avList;
  }
}

package com.client;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FlightMarshaller {
  
  public static byte[] marshall(ArrayList<AttributeValue> arrayAV){
    byte[] finalByteArray = new byte[1024];
    int curLength = 0;
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
    byte[] temp = ByteConverter.convertToBytes(new AttributeValueString("FINAL", ""));
    System.arraycopy(temp, 0, finalByteArray, curLength, temp.length);
    return finalByteArray;
  }

  public static ArrayList<AttributeValue> unmarshall(byte[] bytes) {
    boolean isAttribute = true;
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    ArrayList<AttributeValue> avList = new ArrayList<>();

    String globalAttribute = "";
    while(true){
      if(isAttribute){
        int attributeLength = buffer.getInt();
        String attribute = "";
        for(int i = 0; i < attributeLength; i++){
          Character temp = (char) (buffer.get() & 0xFF);
          attribute += temp;
        }
        if(attribute.equals("FINAL")){
          break;
        };
        globalAttribute = attribute;
        isAttribute = false;
      }
      else{
        Character type = (char)(buffer.get() & 0xFF);
        int valueLength = buffer.getInt();
        if(type == 'S'){
          String value = "";
          for(int i = 0; i < valueLength; i++){
            Character temp = (char) (buffer.get() & 0xFF);
            value += temp;
          }
          avList.add(new AttributeValueString(globalAttribute, value));
        }
        else if(type == 'I'){
          Integer value = buffer.getInt();
          avList.add(new AttributeValueInt(globalAttribute, value));
        }
        else if(type == 'F'){
          Float value = buffer.getFloat();
          avList.add(new AttributeValueFloat(globalAttribute, value));
        }
        isAttribute = true;
      }
    }
    return avList;
  }
}

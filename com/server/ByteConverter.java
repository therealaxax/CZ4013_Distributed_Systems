package com.server;
import java.nio.ByteBuffer;

public class ByteConverter {
  
  // Convert Attribute-Value pair into bytes where value is a String
  public static byte[] convertToBytes(AttributeValueString av) {
    byte[] attributeBytes = av.attribute.getBytes();
    byte[] valueBytes = av.value.getBytes();
    ByteBuffer buffer = ByteBuffer.allocate(4 + attributeBytes.length + 1 + 4 + valueBytes.length);
    buffer.putInt(attributeBytes.length);
    buffer.put(attributeBytes);
    buffer.put("S".getBytes());
    buffer.putInt(valueBytes.length);
    buffer.put(valueBytes);
    return buffer.array();
  }

  // Convert Attribute-Value pair into bytes where value is an Integer
  public static byte[] convertToBytes(AttributeValueInt av) {
    byte[] attributeBytes = av.attribute.getBytes();
    ByteBuffer buffer = ByteBuffer.allocate(4 + attributeBytes.length + 1 + 4 + 4);
    buffer.putInt(attributeBytes.length);
    buffer.put(attributeBytes);
    buffer.put("I".getBytes());
    buffer.putInt(4);
    buffer.putInt(av.value);
    return buffer.array();
  }

  // Convert Attribute-Value pair into bytes where value is a Float
  public static byte[] convertToBytes(AttributeValueFloat av) {
    byte[] attributeBytes = av.attribute.getBytes();
    ByteBuffer buffer = ByteBuffer.allocate(4 + attributeBytes.length + 1 + 4 + 4);
    buffer.putInt(attributeBytes.length);
    buffer.put(attributeBytes);
    buffer.put("F".getBytes());
    buffer.putInt(4);
    buffer.putFloat(av.value);
    return buffer.array();
  }

  // Convert Attribute-Value pair into bytes where value is a Double
  public static byte[] convertToBytes(AttributeValueDouble av) {
    byte[] attributeBytes = av.attribute.getBytes();
    ByteBuffer buffer = ByteBuffer.allocate(4 + attributeBytes.length + 1 + 4 + 8);
    buffer.putInt(attributeBytes.length);
    buffer.put(attributeBytes);
    buffer.put("D".getBytes());
    buffer.putInt(8);
    buffer.putDouble(av.value);
    return buffer.array();
  }

  // Convert Attribute-Value pair into bytes where value is a Boolean
  public static byte[] convertToBytes(AttributeValueBoolean av) {
    byte[] attributeBytes = av.attribute.getBytes();
    byte[] valueBytes = av.value.getBytes();
    ByteBuffer buffer = ByteBuffer.allocate(4 + attributeBytes.length + 1 + 4 + 1);
    buffer.putInt(attributeBytes.length);
    buffer.put(attributeBytes);
    buffer.put("B".getBytes());
    buffer.putInt(1);
    buffer.put(valueBytes);
    return buffer.array();
  }
}

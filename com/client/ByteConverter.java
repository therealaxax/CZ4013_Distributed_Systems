package com.client;
import java.nio.ByteBuffer;

public class ByteConverter {
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
}

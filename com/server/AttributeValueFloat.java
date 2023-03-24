package com.server;

public class AttributeValueFloat extends AttributeValue {
  Float value;
  public AttributeValueFloat(String attribute, Float value){
    super(attribute);
    this.value = value;
  }
  public Float getValue() {
    return value;
  }
  public void setValue(Float value) {
    this.value = value;
  }
}

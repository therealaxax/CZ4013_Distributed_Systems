package com.client;

public class AttributeValueFloat extends AttributeValue {
  Float value;

  AttributeValueFloat(String attribute, Float value){
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

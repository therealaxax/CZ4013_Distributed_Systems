package com.server;

public class AttributeValueDouble extends AttributeValue {
  Double value;

  AttributeValueDouble(String attribute, Double value){
    super(attribute);
    this.value = value;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }
}

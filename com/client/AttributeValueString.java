package com.client;

public class AttributeValueString extends AttributeValue {
  String value;

  AttributeValueString(String attribute, String value){
    super(attribute);
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}

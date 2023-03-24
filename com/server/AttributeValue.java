package com.server;

import java.io.Serializable;

public class AttributeValue implements Serializable {
  String attribute;
  public AttributeValue(){
    this.attribute = "";
  }
  public AttributeValue(String attribute){
    this.attribute = attribute;
  }
  public String getAttribute() {
    return attribute;
  }
  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }
}

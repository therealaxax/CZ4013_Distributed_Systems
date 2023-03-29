package com.client;

public class AttributeValueBoolean extends AttributeValue {
  String value;

  AttributeValueBoolean(String attribute, Boolean value){
    super(attribute);
    if(value == false) this.value = "0";
    else this.value = "1";
  }

  public Boolean getValue() {
    if(value == "0") return false;
    else return true;
  }

  public void setValue(Boolean value) {
    if(value == false) this.value = "0";
    else this.value = "1";
  }
}

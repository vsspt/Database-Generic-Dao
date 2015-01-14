package com.github.vsspt.common.db.dao.api.util;

public class Parameter {

  private String key;
  private Object value;

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(final Object value) {
    this.value = value;
  }
}

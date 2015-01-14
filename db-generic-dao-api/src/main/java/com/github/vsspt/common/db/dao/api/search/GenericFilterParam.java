package com.github.vsspt.common.db.dao.api.search;

public class GenericFilterParam {
  final String name;
  final Object value;

  public GenericFilterParam(final String name, final Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

}

package com.github.vsspt.common.db.dao.api.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericFilterDef {
  private final String name;
  private final List<GenericFilterParam> parameters = new ArrayList<GenericFilterParam>();

  public GenericFilterDef(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addParameter(final GenericFilterParam param) {
    parameters.add(param);
  }

  public Boolean hasParameters() {
    return !parameters.isEmpty();
  }

  public List<GenericFilterParam> getParameters() {
    return parameters;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    final GenericFilterDef other = (GenericFilterDef) obj;

    return Objects.equals(name, other.name);
  }

}

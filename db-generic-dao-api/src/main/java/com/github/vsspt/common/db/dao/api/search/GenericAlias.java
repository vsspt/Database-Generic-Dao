package com.github.vsspt.common.db.dao.api.search;

import java.util.Objects;

public class GenericAlias {
  private final String aliasKey;
  private final String aliasValue;
  private final String property;
  private final Boolean isAlias;
  private static final String PAIR_FORMAT = "KEY_%s_%s";
  private static final String ALIAS_FORMAT = "%s.%s";
  private static final String TO_STRING_FORMAT = "Alias = [(%s,%s)]; Property = [%s]";

  public GenericAlias(final String aliasKey, final String aliasValue, final String property) {
    this.aliasKey = aliasKey;
    this.aliasValue = aliasValue;
    this.property = property;
    this.isAlias = Boolean.TRUE;
  }

  public GenericAlias(final String property) {
    this.property = property;
    this.aliasKey = null;
    this.aliasValue = null;
    this.isAlias = Boolean.FALSE;
  }

  public String getPairKey() {
    return String.format(PAIR_FORMAT, aliasKey, aliasValue);
  }

  public String getAliasKey() {
    return aliasKey;
  }

  public String getAliasValue() {
    return aliasValue;
  }

  public Boolean isAlias() {
    return isAlias;
  }

  public String getAlias() {
    return isAlias ? String.format(ALIAS_FORMAT, aliasValue, property) : property;
  }

  @Override
  public String toString() {
    return String.format(TO_STRING_FORMAT, aliasKey, aliasValue, getAlias());
  }

  @Override
  public int hashCode() {
    return Objects.hash(aliasKey, aliasValue, property);
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
    final GenericAlias other = (GenericAlias) obj;

    return Objects.equals(aliasKey, other.aliasKey) && Objects.equals(aliasValue, other.aliasValue) && Objects.equals(property, other.property);

  }

}

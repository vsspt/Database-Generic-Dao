package com.github.vsspt.common.db.dao.api.search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.googlecode.genericdao.search.ISearch;

public class GenericSearch {
  private final ISearch search;
  private final List<GenericAlias> aliases = new ArrayList<GenericAlias>();
  private final List<GenericFilterDef> filtersDef = new ArrayList<GenericFilterDef>();

  public GenericSearch(final ISearch search) {
    this.search = search;
  }

  public void addAlias(final GenericAlias alias) {
    if (alias != null && alias.isAlias()) {
      if (!aliases.contains(alias)) {
        aliases.add(alias);

      }
    }
  }

  public void addAllFilters(final List<GenericFilterDef> list) {
    for (final GenericFilterDef filter : list) {
      addFilter(filter);
    }
  }

  public void addFilter(final GenericFilterDef filter) {
    if (filter != null && filter.getName() != null) {
      if (!filtersDef.contains(filter)) {
        filtersDef.add(filter);
      }
    }
  }

  public void addAll(final List<GenericAlias> list) {
    for (final GenericAlias alias : list) {
      addAlias(alias);
    }
  }

  public ISearch getSearch() {
    return search;
  }

  public List<GenericAlias> getAliases() {
    return aliases;
  }

  public List<GenericFilterDef> getFiltersDef() {
    return filtersDef;
  }

  public List<GenericAlias> getDistinctAliases() {
    final Map<String, GenericAlias> distinct = new LinkedHashMap<String, GenericAlias>();

    for (final GenericAlias alias : aliases) {
      final String key = alias.getPairKey();
      if (!distinct.containsKey(key)) {
        distinct.put(key, alias);
      }
    }
    return new LinkedList<GenericAlias>(distinct.values());
  }

  public Boolean hasAliases() {
    return aliases.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
  }

  public Boolean hasFilters() {
    return filtersDef.isEmpty() ? Boolean.FALSE : Boolean.TRUE;
  }
}

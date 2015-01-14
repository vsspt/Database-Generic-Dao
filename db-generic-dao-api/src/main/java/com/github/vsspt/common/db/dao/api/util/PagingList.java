package com.github.vsspt.common.db.dao.api.util;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PagingList<T> implements Serializable {

  private final int total;
  private final List<T> list;

  public PagingList(final List<T> list, final int total) {
    this.list = list;
    this.total = total;
  }

  public int getTotalRecords() {
    return total;
  }

  public List<T> getList() {
    return list;
  }

}

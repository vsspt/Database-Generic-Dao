package com.github.vsspt.common.db.impl.hibernate.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.googlecode.genericdao.search.Filter;

@SuppressWarnings("serial")
public class FilterToCriteria implements Serializable {

  private static final int TWO_ARGS = 2;
  private static final int IDX_0 = 0;
  private static final int IDX_1 = 1;
  private static final String LIKE_FORMAT = "%s%%";

  private final List<Filter> filters;

  public FilterToCriteria(final List<Filter> filters) {
    this.filters = filters;
  }

  private Criterion getCriterion(final Filter filter) {
    Criterion criterion = null;

    if (filter.isTakesSingleValue()) {

      criterion = getCriterionSingleValue(filter);

    } else if (filter.isTakesListOfValues()) {

      criterion = getCriterionListOfValues(filter);

    } else if (filter.isTakesNoValue()) {

      criterion = getCriterionTakesNoValue(filter);

    } else if (filter.isTakesSingleSubFilter()) {

      criterion = getCriterionTakesSingleSubFilter(filter);

    } else if (filter.isTakesListOfSubFilters()) {

      criterion = getCriterionTakesListOfSubFilters(filter);

    } else if (filter.isTakesNoProperty()) {
      criterion = getCriterionTakesNoProperty(filter);
    }

    return criterion;
  }

  private Criterion getCriterionListOfValues(final Filter filter) {

    final int operator = filter.getOperator();

    switch (operator) {

      case Filter.OP_IN:
        return Restrictions.in(filter.getProperty(), filter.getValuesAsCollection());

      case Filter.OP_NOT_IN:
        return Restrictions.not(Restrictions.in(filter.getProperty(), filter.getValuesAsCollection()));

      default:
        return null;
    }
  }

  public List<Criterion> getCriterions() {
    final List<Criterion> criterions = new LinkedList<Criterion>();

    if (filters != null && !filters.isEmpty()) {

      for (final Filter filter : filters) {

        final Criterion criterion = getCriterion(filter);

        if (criterion != null) {
          criterions.add(criterion);
        }
      }
    }

    return criterions;
  }

  private List<Criterion> getCriterions(final List<Filter> filters) {

    final List<Criterion> criterions = new ArrayList<Criterion>();

    for (final Filter filter : filters) {
      final Criterion criterion = getCriterion(filter);
      if (criterion != null) {
        criterions.add(criterion);
      }
    }

    return criterions;
  }

  private Criterion getCriterionSingleValue(final Filter filter) {

    final int operator = filter.getOperator();

    switch (operator) {

      case Filter.OP_EQUAL:
        return Restrictions.eq(filter.getProperty(), filter.getValue());
      case Filter.OP_NOT_EQUAL:
        return Restrictions.ne(filter.getProperty(), filter.getValue());

      case Filter.OP_LESS_THAN:
        return Restrictions.lt(filter.getProperty(), filter.getValue());

      case Filter.OP_GREATER_THAN:
        return Restrictions.gt(filter.getProperty(), filter.getValue());

      case Filter.OP_LESS_OR_EQUAL:
        return Restrictions.le(filter.getProperty(), filter.getValue());

      case Filter.OP_GREATER_OR_EQUAL:
        return Restrictions.ge(filter.getProperty(), filter.getValue());

      case Filter.OP_LIKE:
        return Restrictions.like(filter.getProperty(), String.format(LIKE_FORMAT, filter.getValue()));

      case Filter.OP_ILIKE:
        return Restrictions.ilike(filter.getProperty(), filter.getValue());

      default:
        return null;
    }

  }

  @SuppressWarnings("unchecked")
  private Criterion getCriterionTakesListOfSubFilters(final Filter filter) {

    final int operator = filter.getOperator();

    switch (operator) {

      case Filter.OP_AND:
        final List<Filter> filtersAnd = (List<Filter>) filter.getValuesAsList();
        final List<Criterion> criterionsAnd = getCriterions(filtersAnd);
        if (criterionsAnd.size() == TWO_ARGS) {
          return Restrictions.and(criterionsAnd.get(IDX_0), criterionsAnd.get(IDX_1));
        }

      case Filter.OP_OR:
        final List<Filter> filtersOr = (List<Filter>) filter.getValuesAsList();
        final List<Criterion> criterionsOr = getCriterions(filtersOr);
        if (criterionsOr.size() == TWO_ARGS) {
          return Restrictions.or(criterionsOr.get(IDX_0), criterionsOr.get(IDX_1));
        }

      default:
        return null;
    }

  }

  private Criterion getCriterionTakesNoProperty(final Filter filter) {

    final int operator = filter.getOperator();

    switch (operator) {

    // TODO :
      case Filter.OP_AND:

      case Filter.OP_OR:

      case Filter.OP_NOT:

      default:
        return null;
    }

  }

  private Criterion getCriterionTakesNoValue(final Filter filter) {

    final int operator = filter.getOperator();

    switch (operator) {

      case Filter.OP_NULL:
        return Restrictions.isNull(filter.getProperty());

      case Filter.OP_NOT_NULL:
        return Restrictions.isNotNull(filter.getProperty());

      case Filter.OP_EMPTY:
        return Restrictions.isEmpty(filter.getProperty());

      case Filter.OP_NOT_EMPTY:
        return Restrictions.isNotEmpty(filter.getProperty());

      default:
        return null;
    }
  }

  private Criterion getCriterionTakesSingleSubFilter(final Filter filter) {

    final int operator = filter.getOperator();

    switch (operator) {

    // TODO :
      case Filter.OP_NOT:

      case Filter.OP_ALL:

      case Filter.OP_SOME:

      case Filter.OP_NONE:

      default:
        return null;
    }

  }

}
